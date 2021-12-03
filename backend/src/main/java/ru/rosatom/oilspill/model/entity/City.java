package ru.rosatom.oilspill.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
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

@EqualsAndHashCode
@Data
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @SequenceGenerator(name="generator", sequenceName="city_seq", allocationSize = 1)
    private Long id;
    private String addressCity;
    private String postalCode;
    private String country;
    private String federalDistrict;
    private String regionType;
    private String region;
    private String areaType;
    private String area;
    private String cityType;
    private String city;
    private String settlementType;
    private String settlement;
    private String kladrId;
    private String fiasId;
    private String fiasLevel;
    private String capitalMarker;
    private String okato;
    private String oktmo;
    private String taxOffice;
    private String timezone;
    private String geoLat;
    private String geoLon;
    private String populationCity;
    private String foundationYear;
}
