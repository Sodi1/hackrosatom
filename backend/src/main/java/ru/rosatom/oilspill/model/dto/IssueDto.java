package ru.rosatom.oilspill.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosatom.oilspill.model.enums.IssueStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueDto {
    private Long plantId;
    private String issueTitle;
    private String issueDescription;
    private Boolean isSatellite;
    private IssueStatus issueStatus;
}
