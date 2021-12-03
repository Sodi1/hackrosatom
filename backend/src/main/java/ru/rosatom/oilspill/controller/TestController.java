package ru.rosatom.oilspill.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rosatom.oilspill.model.dto.api.in.PredictRequest;
import ru.rosatom.oilspill.model.dto.api.out.PredictResponse;

@RestController
@RequestMapping("/testapi")
public class TestController {

    @PostMapping
    public ResponseEntity<PredictResponse> test(@RequestBody PredictRequest predictRequest){
        return ResponseEntity.ok(new PredictResponse());
    }
}
