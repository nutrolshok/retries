package net.nutrolshok.retries.strategies.immediate;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

public class SpringImmediateRetry {

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 0))
    public String callApi() {
        // TODO: some code
        return "Result";
    }
}
