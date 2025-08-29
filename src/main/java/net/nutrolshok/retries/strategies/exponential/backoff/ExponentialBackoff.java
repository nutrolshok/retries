package net.nutrolshok.retries.strategies.exponential.backoff;

import net.nutrolshok.retries.util.RetryableTask;

import java.util.concurrent.TimeUnit;

import static net.nutrolshok.retries.util.PseudoCodeUtil.await;

public class ExponentialBackoff {

    public static <T> T retry(RetryableTask<T> task, int maxAttempts, long baseDelay) throws Exception {
        for (int i = 0; i < maxAttempts; i++) {
            try {
                return task.execute();
            } catch (Exception e) {
                if (i == maxAttempts - 1) throw e;
                long delay = (long) (baseDelay * Math.pow(2, i));

                await(delay, TimeUnit.MILLISECONDS);
            }
        }
        throw new IllegalStateException("Should not reach here");
    }
}
