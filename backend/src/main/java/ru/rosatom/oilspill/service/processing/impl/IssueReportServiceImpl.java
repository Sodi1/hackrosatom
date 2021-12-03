package ru.rosatom.oilspill.service.processing.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.rosatom.oilspill.model.dto.IssueReportDto;
import ru.rosatom.oilspill.model.dto.api.IssuePredictReportDto;
import ru.rosatom.oilspill.model.dto.api.in.PredictRequest;
import ru.rosatom.oilspill.model.dto.api.out.PredictResponse;
import ru.rosatom.oilspill.model.entity.FileImage;
import ru.rosatom.oilspill.model.entity.Issue;
import ru.rosatom.oilspill.model.enums.IssueStatus;
import ru.rosatom.oilspill.service.communication.PredictOuterService;
import ru.rosatom.oilspill.service.local.IssueService;
import ru.rosatom.oilspill.service.processing.IssueReportService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class IssueReportServiceImpl implements IssueReportService {

    private final IssueService issueService;
    private final PredictOuterService predictOuterService;

    @Override
    public IssuePredictReportDto buildReport(Long issueId) {

        IssueReportDto issueReportDto = issueService.findByIssueId(issueId);

        if (issueReportDto != null) {

            Optional<List<FileImage>> imagesOpt = Optional.of(issueReportDto).map(IssueReportDto::getFileImages);

            List<String> images = new ArrayList<>();

            if (imagesOpt.isPresent()) {

                images = imagesOpt
                        .get()
                        .stream()
                        .map(t -> t.getFilePath().replace("https://oil.kovalev.team/back-img/", ""))
                        .collect(Collectors.toList());

            }

            var request = PredictRequest.builder()
                    .issueId(issueReportDto.getIssueId())
                    .img(images)
                    .build();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            try {
                String str = objectMapper.writeValueAsString(request);
                System.out.println(str);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            PredictResponse predictResponse = predictOuterService.predict(request);

            Optional<Issue> issueOpt = issueService.findById(issueId);

            if (issueOpt.isPresent()) {

                Issue issue = issueOpt.get();

                if (issue.getIssueStatus() != null
                        && !issue.getIssueStatus().equals(IssueStatus.INACTIVE)) {

                    issue.setIssueStatus(IssueStatus.INACTIVE);

                    issueService.save(issue);

                }

            }

            return IssuePredictReportDto.builder()
                    .issueReportDto(issueReportDto)
                    .predict(predictResponse)
                    .build();


        }
        return new IssuePredictReportDto();
    }
}
