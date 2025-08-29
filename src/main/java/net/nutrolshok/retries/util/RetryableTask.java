package net.nutrolshok.retries.util;

@FunctionalInterface
public interface RetryableTask<T> {

    T execute() throws Exception;
}