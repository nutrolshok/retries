package net.nutrolshok.retries.patterns.selective;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import java.io.IOException;

public class SpringSelectiveRetry {

    @Retryable(
            maxAttempts = 3,
            retryFor = { IOException.class },
            noRetryFor = { IllegalArgumentException.class },
            backoff = @Backoff(delay = 1000)
    )
    public String callApi() {
        // TODO: some code...
        return "Result";
    }
}
