package com.microsphere.faulttolerance;

import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException;
import org.eclipse.microprofile.faulttolerance.exceptions.FaultToleranceException;

import java.time.temporal.ChronoUnit;
import java.util.function.Supplier;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public interface Retry {

    <T> T execute(Supplier<T> supplier);

    void execute(Runnable runnable);

    final class Options {

        private int maxRetries = 3;

        private long delay = 0;

        /**
         * @see org.eclipse.microprofile.faulttolerance.Retry.delayUnit
         */
        private ChronoUnit delayUnit = ChronoUnit.SECONDS;

        /**
         * @see org.eclipse.microprofile.faulttolerance.Retry.retryOn
         */
        @SuppressWarnings("unchecked")
        private Class<? extends Throwable>[] retryOn = new Class[]{Exception.class};

        /**
         * @see org.eclipse.microprofile.faulttolerance.Retry.abortOn
         */
        @SuppressWarnings("unchecked")
        private Class<? extends Throwable>[] abortOn = new Class[]{FaultToleranceException.class, CircuitBreakerOpenException.class};

        public Options setMaxRetries(int maxRetries) {
            this.maxRetries = maxRetries;

            return this;
        }

        public Options setDelay(long delay) {
            this.delay = delay;

            return this;
        }

        public Options setDelayUnit(ChronoUnit delayUnit) {
            this.delayUnit = delayUnit;

            return this;
        }

        public Options setRetryOn(Class<? extends Throwable>[] retryOn) {
            this.retryOn = retryOn;

            return this;
        }

        public Options setAbortOn(Class<? extends Throwable>[] abortOn) {
            this.abortOn = abortOn;

            return this;
        }

        public int getMaxRetries() {
            return maxRetries;
        }

        public long getDelay() {
            return delay;
        }

        public ChronoUnit getDelayUnit() {
            return delayUnit;
        }

        public Class<? extends Throwable>[] getRetryOn() {
            return retryOn;
        }

        public Class<? extends Throwable>[] getAbortOn() {
            return abortOn;
        }
    }

}
