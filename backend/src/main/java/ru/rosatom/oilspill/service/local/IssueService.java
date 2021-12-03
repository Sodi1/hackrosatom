package ru.rosatom.oilspill.service.local;

import ru.rosatom.oilspill.model.dto.IssueDto;
import ru.rosatom.oilspill.model.dto.IssueReportDto;
import ru.rosatom.oilspill.model.entity.Issue;
import ru.rosatom.oilspill.model.enums.IssueStatus;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    Issue save(Issue issue);

    Issue save(IssueDto issueDto);

    List<IssueReportDto> findAllIssues(List<IssueStatus> issueStatus);

    List<IssueReportDto> findByPlantId(Long plantId);

    IssueReportDto findByIssueId(Long issueId);

    Optional<Issue> findById(Long issueId);

    void delete(Long id);
}
