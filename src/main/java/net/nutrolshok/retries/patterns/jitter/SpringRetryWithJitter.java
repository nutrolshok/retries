package net.nutrolshok.retries.patterns.jitter;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

public class SpringRetryWithJitter {

    @Retryable(
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, random = true)
    )
    public String callApi() {
        // TODO: some code...
        return "Result";
    }
}
