package org.example.firstlabis.dto.socket;

import com.fasterxml.jackson.annotation.JsonProperty;

public record  CarSocketMessageDTO(
        @JsonProperty(value = "car_id")
        Long car_id,

        @JsonProperty(value = "name")
        String name,

        @JsonProperty(value = "cool")
        Boolean cool
) {
}
