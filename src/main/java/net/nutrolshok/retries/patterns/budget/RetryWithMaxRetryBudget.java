package net.nutrolshok.retries.patterns.budget;

import net.nutrolshok.retries.util.RetryableTask;

import java.util.concurrent.TimeUnit;

import static net.nutrolshok.retries.util.PseudoCodeUtil.await;

public class RetryWithMaxRetryBudget {

    public static <T> T retry(RetryableTask<T> task, int maxRetries, long delayMs) throws Exception {
        int retriesLeft = maxRetries;
        while (retriesLeft-- > 0) {
            try {
                return task.execute();
            } catch (Exception e) {
                if (retriesLeft == 0) throw e;

                await(delayMs, TimeUnit.MILLISECONDS);
            }
        }
        throw new IllegalStateException("Should not reach here");
    }
}
