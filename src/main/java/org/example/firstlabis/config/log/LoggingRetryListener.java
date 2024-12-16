package org.example.firstlabis.config.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;


@Slf4j
public class LoggingRetryListener implements RetryListener {
    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        log.info("Начало попыток для операции: {}. Поток: {}",
                context.getAttribute("context.name"),
                Thread.currentThread().getName());
        return true;
    }

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        if (throwable == null) {
            log.info("Операция {} завершена успешно. Поток: {}",
                    context.getAttribute("context.name"),
                    Thread.currentThread().getName());
        } else {
            log.error("Операция {} завершилась ошибкой после всех попыток: {}. Поток: {}",
                    context.getAttribute("context.name"),
                    throwable.getMessage(),
                    Thread.currentThread().getName());
        }
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        log.warn("Ошибка в попытке {} для операции {}: {}. Поток: {}",
                context.getRetryCount(),
                context.getAttribute("context.name"),
                throwable.getMessage(),
                Thread.currentThread().getName());
    }
}
