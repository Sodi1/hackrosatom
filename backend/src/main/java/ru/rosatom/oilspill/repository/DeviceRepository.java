package ru.rosatom.oilspill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rosatom.oilspill.model.entity.Device;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByPlantId(Long plantId);
}
