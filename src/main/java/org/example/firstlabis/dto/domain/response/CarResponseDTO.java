package org.example.firstlabis.dto.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CarResponseDTO(
        @JsonProperty("id")
        Long id,

        @JsonProperty(value = "name")
        String name,

        @JsonProperty(value = "cool")
        boolean cool
) {
}
