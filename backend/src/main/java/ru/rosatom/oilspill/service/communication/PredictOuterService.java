package ru.rosatom.oilspill.service.communication;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import ru.rosatom.oilspill.model.dto.api.in.PredictRequest;
import ru.rosatom.oilspill.model.dto.api.out.PredictResponse;


/**
 * Взаимодействие с сервисом предикта (анализ фото спутников разлива)
 */
@FeignClient(value = "predict", url = "${outer.services.predict.url}")
public interface PredictOuterService {

    @PostMapping(value = "/predict")
    PredictResponse predict(PredictRequest request);

}
