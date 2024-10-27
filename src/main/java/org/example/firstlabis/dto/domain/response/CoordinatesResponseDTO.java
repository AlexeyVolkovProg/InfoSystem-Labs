package org.example.firstlabis.dto.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;


public record CoordinatesResponseDTO(

        @JsonProperty(value = "x")
        int x,

        @JsonProperty(value = "y")
        Double y
) {
}
