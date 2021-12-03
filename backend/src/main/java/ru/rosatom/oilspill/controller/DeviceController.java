package ru.rosatom.oilspill.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rosatom.oilspill.model.dto.DeviceDto;
import ru.rosatom.oilspill.model.entity.Device;
import ru.rosatom.oilspill.service.local.DeviceService;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "entity")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/device")
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping
    public ResponseEntity<List<Device>> findAll() {
        return ResponseEntity.ok(deviceService.findAll());
    }

    @PostMapping
    public ResponseEntity<Device> save(@RequestBody @Valid DeviceDto device) {
        return ResponseEntity.ok(deviceService.save(device));
    }

}
