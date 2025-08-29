package net.nutrolshok.retries.strategies.immediate;

import net.nutrolshok.retries.util.RetryableTask;

public class ImmediateRetry {

    public static <T> T retry(RetryableTask<T> task, int maxAttempts) throws Exception {
        for (int i = 0; i < maxAttempts; i++) {
            try {
                return task.execute();
            } catch (Exception e) {
                if (i == maxAttempts - 1) throw e;
                // no await
            }
        }
        throw new IllegalStateException("Should not reach here");
    }
}
