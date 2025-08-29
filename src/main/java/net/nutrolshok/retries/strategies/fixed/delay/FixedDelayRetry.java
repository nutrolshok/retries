package net.nutrolshok.retries.strategies.fixed.delay;

import net.nutrolshok.retries.util.RetryableTask;

import java.util.concurrent.TimeUnit;

import static net.nutrolshok.retries.util.PseudoCodeUtil.await;

public class FixedDelayRetry {

    public static <T> T retry(RetryableTask<T> task, int maxAttempts, long delayMs) throws Exception {
        for (int i = 1; i <= maxAttempts; i++) {
            try {
                return task.execute();
            } catch (Exception e) {
                if (i == maxAttempts) throw e;

                await(delayMs, TimeUnit.MILLISECONDS);
            }
        }
        throw new IllegalStateException("Should not reach here");
    }
}
