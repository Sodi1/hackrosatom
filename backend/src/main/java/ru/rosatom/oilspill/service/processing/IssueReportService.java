package ru.rosatom.oilspill.service.processing;

import ru.rosatom.oilspill.model.dto.api.IssuePredictReportDto;

public interface IssueReportService {

    IssuePredictReportDto buildReport(Long issueId);

}
