package ru.rosatom.oilspill.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "spill")
public class Spill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @SequenceGenerator(name="generator", sequenceName="spill_seq", allocationSize = 1, initialValue = 90)
    private Long id;
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
