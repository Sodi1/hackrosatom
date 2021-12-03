package ru.rosatom.oilspill.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosatom.oilspill.model.entity.City;
import ru.rosatom.oilspill.model.entity.Device;
import ru.rosatom.oilspill.model.entity.FileImage;
import ru.rosatom.oilspill.model.enums.IssueStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueReportDto {
    private Long issueId;
    private Long plantId;
    private String issueTitle;
    private String issueDescription;
    private Boolean isSatellite;
    private List<FileImage> fileImages;
    private List<Device> triggeredDevices;
    private List<City> affectedCity;
    private IssueStatus issueStatus;
    private LocalDateTime createdAt;
}
