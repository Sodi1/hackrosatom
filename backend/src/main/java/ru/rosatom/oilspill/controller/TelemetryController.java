package ru.rosatom.oilspill.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.rosatom.oilspill.model.dto.TelemetryDto;
import ru.rosatom.oilspill.model.entity.Telemetry;
import ru.rosatom.oilspill.service.local.TelemetryService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Entity")
@RequestMapping("/api/telemetry")
public class TelemetryController {

    private final TelemetryService telemetryService;

    @GetMapping
    public ResponseEntity<List<Telemetry>> findAll(){
        return ResponseEntity.ok(telemetryService.findAll());
    }

    @PostMapping
    public ResponseEntity<Telemetry> save(@RequestBody @Valid @NotNull TelemetryDto telemetryDto) {
        return ResponseEntity.ok(telemetryService.save(telemetryDto));
    }

    @GetMapping("/find")
    public ResponseEntity<List<Telemetry>> find(@RequestParam(value = "deviceId") Long deviceId,
                                                @RequestParam("size") Integer size){
        return ResponseEntity.ok(telemetryService.findByDeviceIdOrderByCreatedAtDesc(deviceId, size));
    }
}
