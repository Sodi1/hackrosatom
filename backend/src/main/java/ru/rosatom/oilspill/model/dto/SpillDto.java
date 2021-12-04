package ru.rosatom.oilspill.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SpillDto {
    private String plant;
    private String licenseArea;
    private String numberOf;
    private String priorityPollutantType;
    private String registrationNumber;
    private String registrationNumberRegister;
    private String oldRegistrationNumbers;
    private String locationContaminatedSite;
    private String administrativeRegion;
    private String lon;
    private String lan;
    private LocalDateTime dateOfRegistrationRegister;
    private LocalDateTime dateOfLastSpill;
    private String technicalInvestigationAct;
    private String dateOf;
    private String landCategoryBefPollution;
    private String availSpecialProtZone;
    private String contaminatedSiteArea;
    private String levelPollutionSoils;
    private String levelOfOil;
    private String yearReclamation;
}
