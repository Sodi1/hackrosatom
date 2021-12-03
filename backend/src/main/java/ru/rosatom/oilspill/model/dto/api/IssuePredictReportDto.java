package ru.rosatom.oilspill.model.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosatom.oilspill.model.dto.IssueReportDto;
import ru.rosatom.oilspill.model.dto.api.out.PredictResponse;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IssuePredictReportDto {
    private IssueReportDto issueReportDto;
    private PredictResponse predict;
}
