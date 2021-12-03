package ru.rosatom.oilspill.model.dto;

import lombok.Data;
import ru.rosatom.oilspill.model.enums.SurfaceType;

@Data
public class DeviceDto {
    private Long plantId;
    private String title;
    private String code;
    private SurfaceType surfaceType;
    private String lan;
    private String lon;
}
