package ru.rosatom.oilspill.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rosatom.oilspill.model.dto.SpillDto;
import ru.rosatom.oilspill.model.entity.Spill;
import ru.rosatom.oilspill.service.local.SpillService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/spill")
public class SpillController {

    private final SpillService spillService;

    @PostMapping
    public ResponseEntity<Spill> save(@RequestBody SpillDto spillDto){
        return ResponseEntity.ok(spillService.save(spillDto));
    }

    @GetMapping
    public ResponseEntity<List<Spill>> findAll(){
        return ResponseEntity.ok(spillService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpillDto> findById(@PathVariable Long id){
        return ResponseEntity.of(spillService.findById(id));
    }

}
