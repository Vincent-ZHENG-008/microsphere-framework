package com.microsphere.faulttolerance.core;

import com.microsphere.core.util.Assert;
import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException;
import org.eclipse.microprofile.faulttolerance.exceptions.FaultToleranceException;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public interface Retry extends ProxyCallable {

    final class RetryOptions {

        /**
         * @see org.eclipse.microprofile.faulttolerance.Retry.maxRetries
         */
        private int maxRetries = 3;

        /**
         * @see org.eclipse.microprofile.faulttolerance.Retry.delay
         */
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

        @SuppressWarnings("unchecked")
        public RetryOptions(Map<String, Object> prop) {
            Optional.ofNullable(prop.get("maxRetries")).ifPresent(val -> maxRetries((int) val));
            Optional.ofNullable(prop.get("delay")).ifPresent(val -> delay((long) val));
            Optional.ofNullable(prop.get("delayUnit")).ifPresent(val -> delayUnit((ChronoUnit) val));
            Optional.ofNullable(prop.get("retryOn")).ifPresent(val -> retryOn((Class<? extends Throwable>[]) val));
            Optional.ofNullable(prop.get("abortOn")).ifPresent(val -> abortOn((Class<? extends Throwable>[]) val));
        }

        public RetryOptions maxRetries(int maxRetries) {
            this.maxRetries = maxRetries;

            return this;
        }

        public RetryOptions delay(long delay) {
            this.delay = delay;

            return this;
        }

        public RetryOptions delayUnit(ChronoUnit delayUnit) {
            this.delayUnit = delayUnit;

            return this;
        }

        public RetryOptions retryOn(Class<? extends Throwable>[] retryOn) {
            if (Assert.isEmpty(retryOn)) {
                return this;
            }

            this.retryOn = Arrays.copyOf(retryOn, retryOn.length);
            return this;
        }

        public RetryOptions abortOn(Class<? extends Throwable>[] abortOn) {
            if (Assert.isEmpty(abortOn)) {
                return this;
            }

            this.abortOn = Arrays.copyOf(abortOn, abortOn.length);
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
            return Arrays.copyOf(retryOn, retryOn.length);
        }

        public Class<? extends Throwable>[] getAbortOn() {
            return Arrays.copyOf(abortOn, abortOn.length);
        }
    }

}
