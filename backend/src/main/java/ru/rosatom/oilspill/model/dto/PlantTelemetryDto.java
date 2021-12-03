package ru.rosatom.oilspill.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlantTelemetryDto {
    private PlantDto plant;
    private List<DeviceTelemetryDto> deviceTelemetry;
}
