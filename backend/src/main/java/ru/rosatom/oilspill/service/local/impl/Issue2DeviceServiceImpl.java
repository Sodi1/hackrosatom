package ru.rosatom.oilspill.service.local.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.rosatom.oilspill.model.entity.Issue2Device;
import ru.rosatom.oilspill.repository.Issue2DeviceRepository;
import ru.rosatom.oilspill.service.local.Issue2DeviceService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class Issue2DeviceServiceImpl implements Issue2DeviceService {

    private final Issue2DeviceRepository repository;

    @Override
    public List<Issue2Device> findByPlantId(Long plantId) {
        return repository.findByPlantId(plantId);
    }

    @Override
    public List<Issue2Device> findByIssueId(Long issueId) {
        return repository.findByIssueId(issueId);
    }

    @Override
    public Issue2Device save(Issue2Device issue2Device) {
        return repository.save(issue2Device);
    }
}
