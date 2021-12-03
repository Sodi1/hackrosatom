package ru.rosatom.oilspill.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TelemetryParamDto {
    private String name;
    private Float value;
}
