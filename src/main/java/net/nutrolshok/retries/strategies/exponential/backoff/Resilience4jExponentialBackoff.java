package net.nutrolshok.retries.strategies.exponential.backoff;

import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;

import static net.nutrolshok.retries.util.PseudoCodeUtil.getOrDefault;

public class Resilience4jExponentialBackoff {

    public String externalMethod() {
        var config = RetryConfig.custom()
                .intervalFunction(IntervalFunction.ofExponentialBackoff(1000, 2.0))
                .build();

        var retry = Retry.of("exponentialBackoff", config);
        var supplier = Retry.decorateSupplier(retry, this::callApi);

        return getOrDefault(supplier, "Fallback");
    }

    public String callApi() {
        return "test";
    }
}
