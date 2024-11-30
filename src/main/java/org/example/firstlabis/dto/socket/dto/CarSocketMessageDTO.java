package org.example.firstlabis.dto.socket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CarSocketMessageDTO(
        @JsonProperty(value = "id")
        Long id,

        @JsonProperty(value = "name")
        String name,

        @JsonProperty(value = "cool")
        Boolean cool,

        @JsonProperty("edit_admin_status")
        Boolean editAdminStatus
) {
}
