package ru.rosatom.oilspill.service.local;

import ru.rosatom.oilspill.model.dto.IssueDto;
import ru.rosatom.oilspill.model.dto.IssueReportDto;
import ru.rosatom.oilspill.model.entity.Issue;
import ru.rosatom.oilspill.model.enums.IssueStatus;

import java.util.List;

public interface IssueService {

    Issue save(IssueDto issueDto);

    List<IssueReportDto> findAllIssues(List<IssueStatus> issueStatus);

    List<IssueReportDto> findByPlantId(Long plantId);
}
