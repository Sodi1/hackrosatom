package ru.rosatom.oilspill.model.dto.api.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PredictResponse {

    private Long issueId;
    private Boolean isSatellite;
    private List<ImageResponseDto> img;
}
