package net.nutrolshok.retries.util;

import io.github.resilience4j.core.functions.CheckedSupplier;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@UtilityClass
public class PseudoCodeUtil {

    @SneakyThrows
    public static void await(long timeout, TimeUnit unit) {
        Thread.sleep(unit.toMillis(timeout));
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static void print(String message) {
        System.out.print(message);
    }

    public static boolean contains(Exception e, Class<?>... list) {
        return Arrays.stream(list).anyMatch(c -> c.isInstance(e));
    }

    public static <T> T getOrDefault(Supplier<T> supplier, T defaultValue) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    @SneakyThrows
    public static <T> T getOrDefault(CheckedSupplier<T> supplier, Callable<T> defaultValue) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            return defaultValue.call();
        }
    }
}
