package ru.rosatom.oilspill.service.local.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import ru.rosatom.oilspill.converter.IssueDtoToIssueConverter;
import ru.rosatom.oilspill.model.dto.IssueDto;
import ru.rosatom.oilspill.model.dto.IssueReportDto;
import ru.rosatom.oilspill.model.entity.City;
import ru.rosatom.oilspill.model.entity.Device;
import ru.rosatom.oilspill.model.entity.FileImage;
import ru.rosatom.oilspill.model.entity.Issue;
import ru.rosatom.oilspill.model.entity.Issue2Device;
import ru.rosatom.oilspill.model.enums.IssueStatus;
import ru.rosatom.oilspill.repository.IssueRepository;
import ru.rosatom.oilspill.service.local.CityService;
import ru.rosatom.oilspill.service.local.DeviceService;
import ru.rosatom.oilspill.service.local.IssueService;
import ru.rosatom.oilspill.utils.GeoUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final IssueDtoToIssueConverter issueDtoToIssueConverter;
    private final DeviceService deviceService;
    private final CityService cityService;

    @Override
    public Issue save(IssueDto issueDto) {
        return issueRepository.save(Objects.requireNonNull(issueDtoToIssueConverter.convert(issueDto)));
    }

    public List<IssueReportDto> findAllIssues(List<IssueStatus> issueStatus) {
        List<IssueReportDto> result = new ArrayList<>();
        for(IssueStatus status : issueStatus) {
            List<Issue> issues = issueRepository.findAllByIssueStatus(status);
            if(!issues.isEmpty()) {
                result.addAll(getIssueReportDtos(issues));
            }
        }
       return result;
    }


    @Override
    public List<IssueReportDto> findByPlantId(Long plantId) {
        List<Issue> issues = issueRepository.findByPlantIdOrderByCreatedAtAsc(plantId);

        return getIssueReportDtos(issues);
    }


    private List<IssueReportDto> getIssueReportDtos(List<Issue> issues) {

        List<IssueReportDto> issueDtos = new ArrayList<>();

        List<City> cities = cityService.findAll()
                .stream()
                .filter(x -> Objects.nonNull(x.getGeoLat()))
                .filter(x -> Objects.nonNull(x.getGeoLon()))
                .collect(Collectors.toList());

        for (Issue issue : issues) {

            List<City> affectedCity = new ArrayList<>();

            List<Device> triggeredDevices = new ArrayList<>();

            List<Issue2Device> issue2Devices = issue.getIssue2Devices();

            Hibernate.initialize(issue2Devices);

            for (Issue2Device issue2Device : issue2Devices) {
                Optional<Device> deviceOpt = deviceService.findById(issue2Device.getDeviceId());
                if (deviceOpt.isPresent()) {
                    Device device = deviceOpt.get();
                    triggeredDevices.add(device);
                    if (device.getLan() != null && device.getLon() != null) {
                        List<City> foundCities = cities.parallelStream()
                                .filter(c->GeoUtils.distance(Double.parseDouble(device.getLan()),
                                        Double.parseDouble(c.getGeoLat()), Double.parseDouble(device.getLon()),
                                        Double.parseDouble(c.getGeoLon()), 0, 0) < 10_000)
                                .collect(Collectors.toList());
                        if(!foundCities.isEmpty()){
                            affectedCity.addAll(foundCities);
                        }
                    }
                }
                deviceOpt.ifPresent(triggeredDevices::add);

            }
            List<FileImage> fileImages = issue.getFileImages();

            Hibernate.initialize(fileImages);
            issueDtos.add(new IssueReportDto(
                    issue.getId(),
                    issue.getPlantId(),
                    issue.getIssueTitle(),
                    issue.getIssueDescription(),
                    issue.getIsSatellite(),
                    fileImages,
                    triggeredDevices,
                    affectedCity,
                    issue.getIssueStatus(),
                    issue.getCreatedAt()
            ));
        }
        return issueDtos;
    }
}
