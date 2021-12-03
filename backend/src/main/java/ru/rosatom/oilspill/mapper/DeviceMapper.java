package ru.rosatom.oilspill.mapper;

import org.mapstruct.Mapper;
import ru.rosatom.oilspill.model.dto.DeviceDto;
import ru.rosatom.oilspill.model.entity.Device;

@Mapper
public interface DeviceMapper {

    Device toEntity(DeviceDto device);

}
