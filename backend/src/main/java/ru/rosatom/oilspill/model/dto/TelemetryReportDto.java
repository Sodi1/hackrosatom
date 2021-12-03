package ru.rosatom.oilspill.model.dto;

import lombok.Data;
import ru.rosatom.oilspill.model.enums.TelemetryProtocol;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TelemetryReportDto {
    private Long deviceId;
    private List<TelemetryParamDto> params;
    private LocalDateTime createdAt;
    private TelemetryProtocol protocol;
}
