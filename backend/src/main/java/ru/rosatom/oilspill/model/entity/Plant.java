package ru.rosatom.oilspill.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import ru.rosatom.oilspill.model.enums.PlantKind;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "plant")
public class Plant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @SequenceGenerator(name="generator", sequenceName="plant_seq", allocationSize = 1, initialValue = 90)
    private Long id;
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
    @Enumerated(EnumType.STRING)
    private PlantKind plantKind;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "plant_id")
    private List<Device> devices;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Plant plant = (Plant) o;
        return Objects.equals(id, plant.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
