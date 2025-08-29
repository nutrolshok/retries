package net.nutrolshok.retries.patterns.selective;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;

import java.io.IOException;
import java.time.Duration;

import static net.nutrolshok.retries.util.PseudoCodeUtil.getOrDefault;

public class Resilience4jSelectiveRetry {

    public String externalMethod() {
        var config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(1000))
                .retryExceptions(IOException.class)
                .build();

        var retry = Retry.of("selective", config);
        var supplier = Retry.decorateSupplier(retry, this::callApi);

        return getOrDefault(supplier, "Fallback");
    }

    public String callApi() {
        return "test";
    }
}
