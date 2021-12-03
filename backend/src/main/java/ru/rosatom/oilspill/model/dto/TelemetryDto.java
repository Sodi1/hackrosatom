package ru.rosatom.oilspill.model.dto;

import lombok.Data;
import ru.rosatom.oilspill.model.enums.TelemetryProtocol;

@Data
public class TelemetryDto {
    private Long deviceId;
    private Float tempwater;
    private Float tempair;
    private Float lightwater;
    private Float lighair;
    private Float gas;
    private Float uv;
    private Float oil;
    private TelemetryProtocol protocol;
}
