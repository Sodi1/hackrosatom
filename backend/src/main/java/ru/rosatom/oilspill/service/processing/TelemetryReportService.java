package ru.rosatom.oilspill.service.processing;

import ru.rosatom.oilspill.model.dto.DeviceTelemetryDto;
import ru.rosatom.oilspill.model.dto.PlantTelemetryDto;

import javax.validation.constraints.NotNull;

public interface TelemetryReportService {

    PlantTelemetryDto findByPlantId(@NotNull Long plantId,
                                    @NotNull Integer telemetrySize);

    DeviceTelemetryDto findByDeviceId(@NotNull Long deviceId,
                                      @NotNull Integer telemetrySize);
}
