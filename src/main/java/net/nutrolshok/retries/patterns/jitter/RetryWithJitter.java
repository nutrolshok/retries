package net.nutrolshok.retries.patterns.jitter;

import net.nutrolshok.retries.util.RetryableTask;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static net.nutrolshok.retries.util.PseudoCodeUtil.await;

public class RetryWithJitter {

    public static <T> T retry(RetryableTask<T> task, int maxAttempts, long baseDelay, long jitter) throws Exception {
        var random = new Random();
        for (int i = 0; i < maxAttempts; i++) {
            try {
                return task.execute();
            } catch (Exception e) {
                if (i == maxAttempts - 1) throw e;
                long delay = (long) (baseDelay * Math.pow(2, i));
                long randomized = delay + (long) (random.nextDouble() * jitter);

                await(randomized, TimeUnit.MILLISECONDS);
            }
        }
        throw new IllegalStateException("Should not reach here");
    }
}
