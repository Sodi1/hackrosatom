package ru.rosatom.oilspill.mapper;

import org.mapstruct.Mapper;
import ru.rosatom.oilspill.model.dto.SpillDto;
import ru.rosatom.oilspill.model.entity.Spill;

@Mapper
public interface SpillMapper {

    Spill toEntity(SpillDto spillDto);

    SpillDto toDto(Spill spill);

}
