package net.nutrolshok.retries.strategies.fixed.delay;

import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;

import static net.nutrolshok.retries.util.PseudoCodeUtil.getOrDefault;

public class Resilience4jFixedDelayRetry {

    public String externalMethod() {
        var config = RetryConfig.custom()
                .maxAttempts(3)
                .intervalFunction(IntervalFunction.of(1000))
                .build();

        var retry = Retry.of("fixedDelay", config);
        var supplier = Retry.decorateSupplier(retry, this::callApi);

        return getOrDefault(supplier, "Fallback");
    }

    public String callApi() {
        return "test";
    }
}
