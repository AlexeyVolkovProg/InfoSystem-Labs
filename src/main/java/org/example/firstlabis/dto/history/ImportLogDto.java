package org.example.firstlabis.dto.history;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.firstlabis.dto.authentication.request.UserDto;

import java.time.LocalDateTime;

public record ImportLogDto(
        @JsonProperty(value = "id", required = true)
        Long id,

        @JsonProperty(value = "user", required = true)
        UserDto user,

        @JsonProperty(value = "success", required = true)
        boolean success,

        @JsonProperty(value = "objects_added", required = true)
        Integer objectAdded,

        @JsonProperty(value = "started_time", required = true)
        LocalDateTime startedTime,

        @JsonProperty(value = "finish_time", required = true)
        LocalDateTime finishTime

) {
}
