package net.nutrolshok.retries.testing;

public class RetryableHttpException extends RuntimeException {
    public RetryableHttpException(String msg) {
        super(msg);
    }

    public RetryableHttpException(String msg, Throwable cause) {
        super(msg, cause);
    }
}