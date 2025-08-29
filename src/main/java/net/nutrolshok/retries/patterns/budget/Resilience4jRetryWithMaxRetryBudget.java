package net.nutrolshok.retries.patterns.budget;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;

import java.time.Duration;

import static net.nutrolshok.retries.util.PseudoCodeUtil.getOrDefault;

public class Resilience4jRetryWithMaxRetryBudget {

    public String externalMethod() {
        var config = RateLimiterConfig.custom()
                .limitForPeriod(5)
                .limitRefreshPeriod(Duration.ofMinutes(1))
                .timeoutDuration(Duration.ofMillis(100))
                .build();

        var retry = RateLimiter.of("maxBudget", config);
        var supplier = RateLimiter.decorateSupplier(retry, this::callApi);

        return getOrDefault(supplier, "Fallback");
    }

    public String callApi() {
        return "test";
    }
}
