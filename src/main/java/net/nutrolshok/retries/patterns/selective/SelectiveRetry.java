package net.nutrolshok.retries.patterns.selective;

import net.nutrolshok.retries.util.RetryableTask;

import java.util.concurrent.TimeUnit;

import static net.nutrolshok.retries.util.PseudoCodeUtil.await;
import static net.nutrolshok.retries.util.PseudoCodeUtil.contains;

public class SelectiveRetry {

    public static <T> T retry(RetryableTask<T> task, int maxAttempts, long delayMs, Class<?>... retryOn) throws Exception {
        for (int i = 1; i <= maxAttempts; i++) {
            try {
                return task.execute();
            } catch (Exception e) {
                boolean shouldRetry = contains(e, retryOn);
                if (!shouldRetry || i == maxAttempts) throw e;

                await(delayMs, TimeUnit.MILLISECONDS);
            }
        }

        throw new IllegalStateException("Should not reach here");
    }
}
