package ru.rosatom.oilspill.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.rosatom.oilspill.model.dto.IssueDto;
import ru.rosatom.oilspill.model.entity.Issue;

@Component
public class IssueDtoToIssueConverter implements Converter<IssueDto, Issue> {

    @Override
    public Issue convert(IssueDto issueDto) {
        return Issue.builder()
                .plantId(issueDto.getPlantId())
                .issueTitle(issueDto.getIssueTitle())
                .issueDescription(issueDto.getIssueDescription())
                .issueStatus(issueDto.getIssueStatus())
                .build();
    }
}
