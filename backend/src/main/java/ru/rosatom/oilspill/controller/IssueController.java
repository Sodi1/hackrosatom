package ru.rosatom.oilspill.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.rosatom.oilspill.model.dto.IssueDto;
import ru.rosatom.oilspill.model.dto.IssueReportDto;
import ru.rosatom.oilspill.model.dto.api.IssuePredictReportDto;
import ru.rosatom.oilspill.model.entity.Issue;
import ru.rosatom.oilspill.model.enums.IssueStatus;
import ru.rosatom.oilspill.service.local.IssueService;
import ru.rosatom.oilspill.service.processing.IssueReportService;

import java.util.List;

@RequiredArgsConstructor
@Tag(name = "Entity")
@RestController
@RequestMapping("/api/issue")
public class IssueController {

    private final IssueService issueService;
    private final IssueReportService issueReportService;

    @PostMapping
    public ResponseEntity<Issue> save(@RequestBody IssueDto issueDto) {
        return ResponseEntity.ok(issueService.save(issueDto));
    }

    @GetMapping("/findIssue")
    public ResponseEntity<List<IssueReportDto>> findAllIssue(@RequestParam("issueStatus") List<IssueStatus> issueStatus) {
        return ResponseEntity.ok(issueService.findAllIssues(issueStatus));
    }

    @GetMapping("/findByPlantId")
    public ResponseEntity<List<IssueReportDto>> findByPlant(@RequestParam("plantId") Long plantId) {
        return ResponseEntity.ok(issueService.findByPlantId(plantId));
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<IssueReportDto> findByIssueId(@PathVariable("issueId") Long issueId) {
        return ResponseEntity.ok(issueService.findByIssueId(issueId));
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<Void> delete(@PathVariable("issueId") Long id) {
        issueService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/buildReport")
    public ResponseEntity<IssuePredictReportDto> buildReport(@RequestParam("issueId") Long issueId) {
        return ResponseEntity.ok(issueReportService.buildReport(issueId));
    }
}
