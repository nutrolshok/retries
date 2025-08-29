package net.nutrolshok.retries.strategies.incremental.backoff;

import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;

import static net.nutrolshok.retries.util.PseudoCodeUtil.getOrDefault;

public class Resilience4jIncrementalBackoff {

    public String externalMethod() {
        var config = RetryConfig.custom()
                .intervalFunction(IntervalFunction.of(500, x -> x * 500))
                .build();

        var retry = Retry.of("incremental", config);
        var supplier = Retry.decorateSupplier(retry, this::callApi);

        return getOrDefault(supplier, "Fallback");
    }

    public String callApi() {
        return "test";
    }
}
