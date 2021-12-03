package ru.rosatom.oilspill.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.rosatom.oilspill.model.enums.TelemetryProtocol;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "telemetry")
public class Telemetry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "telemetry_seq", allocationSize = 1, initialValue = 1)
    private Long id;
    private Long deviceId;
    private Float tempwater;
    private Float tempair;
    private Float lightwater;
    private Float lighair;
    private Float gas;
    private Float uv;
    private Float oil;
    @Enumerated(EnumType.STRING)
    private TelemetryProtocol protocol;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
