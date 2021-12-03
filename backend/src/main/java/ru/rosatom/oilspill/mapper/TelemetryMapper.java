package ru.rosatom.oilspill.mapper;

import org.mapstruct.Mapper;
import ru.rosatom.oilspill.model.dto.TelemetryDto;
import ru.rosatom.oilspill.model.entity.Telemetry;

@Mapper
public interface TelemetryMapper {

    Telemetry toEntity(TelemetryDto telemetryDto);
}
