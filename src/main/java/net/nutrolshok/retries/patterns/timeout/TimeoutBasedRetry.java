package net.nutrolshok.retries.patterns.timeout;

import net.nutrolshok.retries.util.RetryableTask;

import java.util.concurrent.TimeUnit;

import static net.nutrolshok.retries.util.PseudoCodeUtil.await;
import static net.nutrolshok.retries.util.PseudoCodeUtil.currentTimeMillis;

public class TimeoutBasedRetry {

    public static <T> T retry(RetryableTask<T> task, long timeoutMs, long delayMs) throws Exception {
        long deadline = currentTimeMillis() + timeoutMs;
        while (true) {
            try {
                return task.execute();
            } catch (Exception e) {
                if (currentTimeMillis() >= deadline) throw e;

                await(delayMs, TimeUnit.MILLISECONDS);
            }
        }
    }
}
