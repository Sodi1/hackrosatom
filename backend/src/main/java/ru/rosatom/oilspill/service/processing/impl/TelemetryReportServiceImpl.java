package ru.rosatom.oilspill.service.processing.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rosatom.oilspill.converter.TelemetryToMapParamsConverter;
import ru.rosatom.oilspill.mapper.PlantMapper;
import ru.rosatom.oilspill.model.dto.DeviceTelemetryDto;
import ru.rosatom.oilspill.model.dto.PlantTelemetryDto;
import ru.rosatom.oilspill.model.dto.TelemetryReportDto;
import ru.rosatom.oilspill.model.entity.Device;
import ru.rosatom.oilspill.model.entity.Plant;
import ru.rosatom.oilspill.model.entity.Telemetry;
import ru.rosatom.oilspill.service.local.impl.DeviceServiceImpl;
import ru.rosatom.oilspill.service.local.impl.PlantServiceImpl;
import ru.rosatom.oilspill.service.local.impl.TelemetryServiceImpl;
import ru.rosatom.oilspill.service.processing.TelemetryReportService;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TelemetryReportServiceImpl implements TelemetryReportService {

    private final PlantServiceImpl plantService;
    private final TelemetryServiceImpl telemetryService;
    private final DeviceServiceImpl deviceService;
    private final PlantMapper plantMapper;
    private final TelemetryToMapParamsConverter telemetryToMapParamsConverter;

    public PlantTelemetryDto findByPlantId(@NotNull Long plantId,
                                           @NotNull Integer telemetrySize) {

        Optional<Plant> plantOpt = plantService.findById(plantId);

        if (plantOpt.isPresent()) {

            var plant = plantOpt.get();
            var plantTelemetryDto = new PlantTelemetryDto();

            plantTelemetryDto.setPlant(plantMapper.toDto(plant));

            List<Device> devices = deviceService.findByPlantId(plantId);
            List<DeviceTelemetryDto> deviceTelemetryDto = new ArrayList<>();

            for (Device device : devices) {

                List<Telemetry> telemetries = telemetryService.findByDeviceIdOrderByCreatedAtDesc(
                        device.getId(),
                        telemetrySize
                );

                List<TelemetryReportDto> telemetryReportDtos = telemetries.stream().map(telemetryToMapParamsConverter::convert).collect(Collectors.toList());

                deviceTelemetryDto.add(new DeviceTelemetryDto(device, telemetryReportDtos));

            }

            plantTelemetryDto.setDeviceTelemetry(deviceTelemetryDto);
            return plantTelemetryDto;
        }

        return new PlantTelemetryDto();
    }

    @Override
    public DeviceTelemetryDto findByDeviceId(Long deviceId, Integer telemetrySize) {

        Optional<Device> deviceOpt = deviceService.findById(deviceId);

        if (deviceOpt.isPresent()) {

            Device device = deviceOpt.get();

            List<Telemetry> telemetryList = telemetryService.findByDeviceIdOrderByCreatedAtDesc(deviceId, telemetrySize);

            List<TelemetryReportDto> telemetryReportDtos = telemetryList
                    .stream()
                    .map(telemetryToMapParamsConverter::convert)
                    .collect(Collectors.toList());

            return new DeviceTelemetryDto(device, telemetryReportDtos);

        }

        return new DeviceTelemetryDto();
    }

}
