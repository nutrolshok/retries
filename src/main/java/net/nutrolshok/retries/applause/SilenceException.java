package net.nutrolshok.retries.applause;

public class SilenceException extends RuntimeException {
    public SilenceException(String message) {
        super(message);
    }
}
