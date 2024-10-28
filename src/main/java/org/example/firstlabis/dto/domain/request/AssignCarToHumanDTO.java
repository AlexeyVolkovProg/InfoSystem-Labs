package org.example.firstlabis.dto.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AssignCarToHumanDTO(
        @JsonProperty(value="car_id", required = true)
        Long carId,
        @JsonProperty(value="human_id", required = true)
        Long humanId
) {
}
