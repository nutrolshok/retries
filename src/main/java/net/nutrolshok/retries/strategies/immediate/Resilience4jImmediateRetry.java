package net.nutrolshok.retries.strategies.immediate;

import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;

import static net.nutrolshok.retries.util.PseudoCodeUtil.getOrDefault;

public class Resilience4jImmediateRetry {

    public String externalMethod() {
        var config = RetryConfig.custom()
                .intervalFunction(IntervalFunction.of(0))
                .build();

        var retry = Retry.of("immediate", config);
        var supplier = Retry.decorateSupplier(retry, this::callApi);

        return getOrDefault(supplier, "Fallback");
    }

    public String callApi() {
        return "test";
    }
}
