package com.microsphere.faulttolerance.core.resilience4j;

import com.microsphere.faulttolerance.core.ConfigConverter;
import com.microsphere.faulttolerance.core.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.eclipse.microprofile.faulttolerance.exceptions.FaultToleranceException;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class Resilience4jRetry implements Retry, ConfigConverter<Retry.RetryOptions, RetryConfig> {

    private final io.github.resilience4j.retry.Retry delegate;

    public Resilience4jRetry(String name, RetryRegistry retryRegistry, RetryOptions ops) {
        this.delegate = retryRegistry.retry(name, convert(ops));
    }

    @Override
    public <T> T execute(Supplier<T> supplier) {
        try {
            return this.delegate.executeSupplier(supplier);
        } catch (Throwable ex) {
            throw new FaultToleranceException(ex);
        }
    }

    @Override
    public RetryConfig convert(RetryOptions ops) {
        return RetryConfig.custom()
                .maxAttempts(ops.getMaxRetries())
                .waitDuration(Duration.of(ops.getDelay(), ops.getDelayUnit()))
                .retryExceptions(ops.getRetryOn())
                .ignoreExceptions(ops.getAbortOn())
                .failAfterMaxAttempts(true)
                .build();
    }
}
