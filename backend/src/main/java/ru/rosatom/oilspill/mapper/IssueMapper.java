package ru.rosatom.oilspill.mapper;

import org.mapstruct.Mapper;
import ru.rosatom.oilspill.model.dto.IssueDto;
import ru.rosatom.oilspill.model.entity.Issue;

@Mapper
public interface IssueMapper {

    Issue toEntity(IssueDto issueDto);

}
