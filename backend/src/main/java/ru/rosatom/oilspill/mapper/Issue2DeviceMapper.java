package ru.rosatom.oilspill.mapper;

import org.mapstruct.Mapper;
import ru.rosatom.oilspill.model.dto.Issue2DeviceDto;
import ru.rosatom.oilspill.model.entity.Issue2Device;

@Mapper
public interface Issue2DeviceMapper {

    Issue2Device toEntity(Issue2DeviceDto issue2DeviceDto);

    Issue2DeviceDto toDto(Issue2Device issue2Device);

}
