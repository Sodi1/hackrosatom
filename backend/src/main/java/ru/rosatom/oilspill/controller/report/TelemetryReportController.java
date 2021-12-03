package ru.rosatom.oilspill.controller.report;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.rosatom.oilspill.model.dto.DeviceTelemetryDto;
import ru.rosatom.oilspill.model.dto.PlantTelemetryDto;
import ru.rosatom.oilspill.service.processing.TelemetryReportService;

@Tag(name = "Report")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/plant/telemetry")
public class TelemetryReportController {

    private final TelemetryReportService telemetryReportService;

    @GetMapping
    public ResponseEntity<PlantTelemetryDto> findByPlantId(@RequestParam("plantId") Long plantId,
                                                           @RequestParam("telemetrySize") Integer telemetrySize) {
        return ResponseEntity.ok(telemetryReportService.findByPlantId(plantId, telemetrySize));
    }

    @GetMapping("/device/find")
    public ResponseEntity<DeviceTelemetryDto> findByDevice(@RequestParam("deviceId") Long deviceId,
                                                           @RequestParam("telemetrySize") Integer telemetrySize){
        return ResponseEntity.ok(telemetryReportService.findByDeviceId(deviceId, telemetrySize));
    }
}
