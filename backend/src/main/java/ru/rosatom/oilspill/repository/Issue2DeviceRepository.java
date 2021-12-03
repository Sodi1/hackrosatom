package ru.rosatom.oilspill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rosatom.oilspill.model.entity.Issue2Device;

import java.util.List;

public interface Issue2DeviceRepository extends JpaRepository<Issue2Device, Long> {

    List<Issue2Device> findByPlantId(Long plantId);

    List<Issue2Device> findByIssueId(Long issueId);

}
