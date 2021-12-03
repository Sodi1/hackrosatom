package ru.rosatom.oilspill.model.dto;

import lombok.Data;
import ru.rosatom.oilspill.model.enums.PlantKind;

@Data
public class PlantDto {
    private String fullName;
    private String shortName;
    private String legalAddress;
    private String factAddress;
    private String processingDepth;
    private String listProduced;
    private String foundationDate;
    private String refineryStatus;
    private String lat;
    private String lon;
    private String balloonContentHeader;
    private String balloonContentBody;
    private String balloonContentFooter;
    private String clusterCaption;
    private Boolean isLeak;
    private PlantKind plantKind;
}
