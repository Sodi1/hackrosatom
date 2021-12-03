package ru.rosatom.oilspill.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rosatom.oilspill.model.dto.PlantDto;
import ru.rosatom.oilspill.model.entity.Plant;
import ru.rosatom.oilspill.service.local.PlantService;

import java.util.List;

@RequiredArgsConstructor
@Tag(name = "entity")
@RestController
@RequestMapping("/api/plant")
public class PlantController {

    private final PlantService plantService;

    @GetMapping
    public ResponseEntity<List<Plant>> findAll(){
        return ResponseEntity.ok(plantService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plant> findById(@PathVariable("id") Long id){
        return ResponseEntity.of(plantService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Plant> save(@RequestBody PlantDto plantDto){
        return ResponseEntity.ok(plantService.save(plantDto));
    }
}
