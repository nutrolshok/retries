package net.nutrolshok.retries.patterns.jitter;

import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;

import static net.nutrolshok.retries.util.PseudoCodeUtil.getOrDefault;

public class Resilience4jRetryWithJitter {

    public String externalMethod() {
        var config = RetryConfig.custom()
                .intervalFunction(IntervalFunction.ofRandomized(1000, 0.5))
                .build();

        var retry = Retry.of("jitter", config);
        var supplier = Retry.decorateSupplier(retry, this::callApi);

        return getOrDefault(supplier, "Fallback");
    }

    public String callApi() {
        return "test";
    }
}