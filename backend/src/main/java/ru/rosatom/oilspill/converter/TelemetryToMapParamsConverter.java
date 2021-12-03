package ru.rosatom.oilspill.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.rosatom.oilspill.model.dto.TelemetryParamDto;
import ru.rosatom.oilspill.model.dto.TelemetryReportDto;
import ru.rosatom.oilspill.model.entity.Telemetry;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelemetryToMapParamsConverter implements Converter<Telemetry, TelemetryReportDto> {

    @Override
    public TelemetryReportDto convert(Telemetry telemetry) {

        var telemetryDto = new TelemetryReportDto();

        List<TelemetryParamDto> telemetryParamDtoList = new ArrayList<>();

        telemetryParamDtoList.add(new TelemetryParamDto("Уровень газа", telemetry.getGas()));
        telemetryParamDtoList.add(new TelemetryParamDto("Свет над водой", telemetry.getLighair()));
        telemetryParamDtoList.add(new TelemetryParamDto("Свет под водой", telemetry.getLightwater()));
        telemetryParamDtoList.add(new TelemetryParamDto("Углеводороды", telemetry.getOil()));
        telemetryParamDtoList.add(new TelemetryParamDto("Температура воздуха", telemetry.getTempair()));
        telemetryParamDtoList.add(new TelemetryParamDto("Температура воды", telemetry.getTempwater()));

        telemetryDto.setDeviceId(telemetry.getDeviceId());
        telemetryDto.setCreatedAt(telemetry.getCreatedAt());
        telemetryDto.setParams(telemetryParamDtoList);
        telemetryDto.setProtocol(telemetry.getProtocol());

        return telemetryDto;
    }
}
