package org.example.firstlabis.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Класс конфигурации супер админа, который уже будет в системе при ее запуске
 */

@Configuration
@ConfigurationProperties(prefix = "admin")
@Setter
@Getter
public class AdminProperties {
    private String username;
    private String password;
}
