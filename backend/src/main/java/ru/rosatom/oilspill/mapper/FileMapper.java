package ru.rosatom.oilspill.mapper;

import org.mapstruct.Mapper;
import ru.rosatom.oilspill.model.dto.FileDto;
import ru.rosatom.oilspill.model.entity.FileImage;

@Mapper
public interface FileMapper {

    FileImage toEntity(FileDto fileDto);

}
