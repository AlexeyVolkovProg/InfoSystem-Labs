package org.example.firstlabis.dto.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CarCreateDTO(
        @JsonProperty(value = "name", required = true)
        String name,

        @JsonProperty(value = "cool", required = true)
        boolean cool
) {

}
