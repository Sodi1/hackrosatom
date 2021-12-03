package ru.rosatom.oilspill.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Issue2DeviceDto {
    private Long plantId;
    private Long issueId;
    private Long deviceId;
    private LocalDateTime createdAt = LocalDateTime.now();
}
