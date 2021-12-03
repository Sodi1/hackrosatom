package ru.rosatom.oilspill.mapper;

import org.mapstruct.Mapper;
import ru.rosatom.oilspill.model.dto.PlantDto;
import ru.rosatom.oilspill.model.entity.Plant;

@Mapper
public interface PlantMapper {

    PlantDto toDto(Plant plant);

    Plant toEntity(PlantDto plantDto);
}
