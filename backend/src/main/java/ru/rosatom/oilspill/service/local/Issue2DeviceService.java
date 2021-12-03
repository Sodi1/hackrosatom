package ru.rosatom.oilspill.service.local;

import ru.rosatom.oilspill.model.entity.Issue2Device;

import java.util.List;

public interface Issue2DeviceService {

    List<Issue2Device> findByPlantId(Long plantId);

    List<Issue2Device> findByIssueId(Long issueId);

    Issue2Device save(Issue2Device issue2Device);
}
