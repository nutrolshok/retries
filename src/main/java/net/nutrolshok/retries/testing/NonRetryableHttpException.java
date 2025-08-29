package net.nutrolshok.retries.testing;

public class NonRetryableHttpException extends RuntimeException {
    public NonRetryableHttpException(String msg) {
        super(msg);
    }

    public NonRetryableHttpException(String msg, Throwable cause) {
        super(msg, cause);
    }
}