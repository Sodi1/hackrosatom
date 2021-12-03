package ru.rosatom.oilspill.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rosatom.oilspill.model.entity.City;
import ru.rosatom.oilspill.service.local.CityService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/city")
@Tag(name = "Entity")
public class CityController {

    private final CityService cityService;

    @GetMapping
    public ResponseEntity<List<City>> findAll() {
        return ResponseEntity.ok(cityService.findAll());
    }

}
