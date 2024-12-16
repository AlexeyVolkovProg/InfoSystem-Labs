package org.example.firstlabis.config.log;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RetryConfig {
    @Bean
    public LoggingRetryListener loggingRetryListener() {
        return new LoggingRetryListener();
    }

    @Bean
    public RetryTemplate retryTemplate(LoggingRetryListener loggingRetryListener) {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.registerListener(loggingRetryListener);
        return retryTemplate;
    }
}
