package ru.rosatom.oilspill.model.dto.api.out;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class ImageResponseDto {
    private String originalImg;
    private String predictImg;
    private Map<String, String> metrics;
}
