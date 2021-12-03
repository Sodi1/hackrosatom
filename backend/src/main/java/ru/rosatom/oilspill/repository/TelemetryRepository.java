package ru.rosatom.oilspill.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.rosatom.oilspill.model.entity.Telemetry;

import java.util.List;

public interface TelemetryRepository extends PagingAndSortingRepository<Telemetry, Long> {
    List<Telemetry> findAll();

    List<Telemetry> findByDeviceIdOrderByCreatedAtDesc(Long deviceId, Pageable pageable);
}
