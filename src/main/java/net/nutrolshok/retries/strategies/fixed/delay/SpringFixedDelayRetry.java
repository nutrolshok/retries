package net.nutrolshok.retries.strategies.fixed.delay;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

public class SpringFixedDelayRetry {

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public String callApi() {
        // TODO: some code
        return "Result";
    }
}
