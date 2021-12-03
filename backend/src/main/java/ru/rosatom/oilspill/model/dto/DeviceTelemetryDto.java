package ru.rosatom.oilspill.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosatom.oilspill.model.entity.Device;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceTelemetryDto {
    private Device device;
    private List<TelemetryReportDto> telemetry;
}
