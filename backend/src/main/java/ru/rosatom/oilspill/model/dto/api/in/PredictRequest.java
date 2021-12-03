package ru.rosatom.oilspill.model.dto.api.in;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PredictRequest {
    private Long issueId;
    private List<String> img;
}
