package org.example.firstlabis.dto.authentication;

import org.example.firstlabis.model.security.Role;

public record RegisterRequestDTO(String username, String password, Role role) {

}
