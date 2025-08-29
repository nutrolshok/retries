package net.nutrolshok.retries.strategies.incremental.backoff;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

public class SpringIncrementalBackoff {

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 1.0))
    public String callApi() {
        // TODO: some code
        return "Result";
    }
}
