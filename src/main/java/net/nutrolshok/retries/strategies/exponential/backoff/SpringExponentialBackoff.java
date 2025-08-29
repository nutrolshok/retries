package net.nutrolshok.retries.strategies.exponential.backoff;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

public class SpringExponentialBackoff {

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2.0))
    public String callApi() {
        // TODO: some code
        return "Result";
    }
}
