package ru.rosatom.oilspill.service.local.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.rosatom.oilspill.mapper.TelemetryMapper;
import ru.rosatom.oilspill.model.dto.IssueDto;
import ru.rosatom.oilspill.model.dto.TelemetryDto;
import ru.rosatom.oilspill.model.entity.Device;
import ru.rosatom.oilspill.model.entity.Issue;
import ru.rosatom.oilspill.model.entity.Issue2Device;
import ru.rosatom.oilspill.model.entity.Plant;
import ru.rosatom.oilspill.model.entity.Telemetry;
import ru.rosatom.oilspill.model.enums.IssueStatus;
import ru.rosatom.oilspill.repository.TelemetryRepository;
import ru.rosatom.oilspill.service.local.DeviceService;
import ru.rosatom.oilspill.service.local.Issue2DeviceService;
import ru.rosatom.oilspill.service.local.IssueService;
import ru.rosatom.oilspill.service.local.PlantService;
import ru.rosatom.oilspill.service.local.TelemetryService;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TelemetryServiceImpl implements TelemetryService {

    private final TelemetryMapper mapper;
    private final TelemetryRepository repository;
    private final IssueService issueService;
    private final DeviceService deviceService;
    private final PlantService plantService;
    private final Issue2DeviceService issue2DeviceService;

    public List<Telemetry> findAll() {
        return repository.findAll();
    }

    public Telemetry save(@NotNull TelemetryDto telemetry) {
        Float gas = telemetry.getGas();
        if (gas >= 2_000.00) {
            Optional<Device> deviceOpt = deviceService.findById(telemetry.getDeviceId());
            if (deviceOpt.isPresent()) {

                Device device = deviceOpt.get();

                var foundIssue = issueService.findByPlantId(device.getPlantId());

                Optional<Plant> plantOpt = plantService.findById(device.getPlantId());
                Plant plant = plantOpt.get();

                if (foundIssue.isEmpty()) {

                    Issue issue = issueService.save(new IssueDto(
                            device.getPlantId(),
                            String.format("Утечка нефти от завода %s", plant.getFullName()),
                            String.format("<b>Координаты</b>: %s %s" +
                                            "<br> <b>Время</b>: %s" +
                                            "<br><b>Уровень газа</b>: %s<br> " +
                                            "<b>Свет над водой</b>: %s " +
                                            "<br> <b>Свет под водой</b>: %s" +
                                            "<br> <b>Углеводороды</b>: %s" +
                                            "<br> <b>Температура воздуха</b>: %s" +
                                            "<br> <b>Температура воды</b>: %s",
                                    device.getLan(), device.getLon(),
                                    LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                                    telemetry.getGas(), telemetry.getLighair(), telemetry.getLightwater(),
                                    telemetry.getOil(),
                                    telemetry.getTempair(),
                                    telemetry.getTempwater()
                            ),
                            false,
                            IssueStatus.WAIT_PHOTO
                    ));
                    issue2DeviceService.save(new Issue2Device(null, plant.getId(), issue.getId(), device.getId(), LocalDateTime.now()));
                } else {
                    var issue = foundIssue.get(0);
                    var issue2Devices = issue2DeviceService.findByIssueId(issue.getIssueId());
                    var foundIssue2Devices = issue2Devices.stream().filter(x -> x.getDeviceId().equals(device.getId())).findAny();
                    if (foundIssue2Devices.isEmpty()) {
                        issue2DeviceService.save(new Issue2Device(null, plant.getId(), issue.getIssueId(), device.getId(), LocalDateTime.now()));
                    }
                }
            }
        }
        return repository.save(mapper.toEntity(telemetry));
    }

    public List<Telemetry> findByDeviceIdOrderByCreatedAtDesc(@NotNull Long deviceId,
                                                              @NotNull Integer size) {
        return repository.findByDeviceIdOrderByCreatedAtDesc(
                deviceId,
                Pageable.ofSize(size)
        );
    }

}
