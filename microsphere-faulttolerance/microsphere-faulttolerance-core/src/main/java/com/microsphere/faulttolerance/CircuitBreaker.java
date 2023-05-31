package com.microsphere.faulttolerance;

import com.microsphere.core.util.Assert;

import java.time.temporal.ChronoUnit;
import java.util.function.Supplier;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public interface CircuitBreaker {

    <T> T execute(Supplier<T> supplier);

    void execute(Runnable runnable);

    class CircuitBreakerOptions {

        /**
         * @see org.eclipse.microprofile.faulttolerance.CircuitBreaker.delay
         */
        private long delay = 5000;

        /**
         * @see org.eclipse.microprofile.faulttolerance.CircuitBreaker.delayUnit
         */
        private ChronoUnit delayUnit = ChronoUnit.MILLIS;

        /**
         * @see org.eclipse.microprofile.faulttolerance.CircuitBreaker.requestVolumeThreshold
         */
        private int requestVolumeThreshold = 20;

        /**
         * @see org.eclipse.microprofile.faulttolerance.CircuitBreaker.failureRatio
         */
        private double failureRatio = .50;

        /**
         * @see org.eclipse.microprofile.faulttolerance.CircuitBreaker.successThreshold
         */
        private int successThreshold = 1;

        public CircuitBreakerOptions delay(long delay) {
            Assert.isTrue(delay < 1000, () -> "delay must bigger than 1000");

            this.delay = delay;
            return self();
        }

        public CircuitBreakerOptions delayUnit(ChronoUnit delayUnit) {
            Assert.notNull(delayUnit, () -> "delayUnit cannot be null");

            this.delayUnit = delayUnit;
            return self();
        }

        public CircuitBreakerOptions requestVolumeThreshold(int requestVolumeThreshold) {
            Assert.isTrue(requestVolumeThreshold < 1, () -> "requestVolumeThreshold must bigger than 1");

            this.requestVolumeThreshold = requestVolumeThreshold;
            return self();
        }

        public CircuitBreakerOptions failureRatio(double failureRatio) {
            Assert.isTrue(failureRatio <= 0, () -> "failureRatio must bigger than 0");

            this.failureRatio = failureRatio;
            return self();
        }

        public CircuitBreakerOptions successThreshold(int successThreshold) {
            Assert.isTrue(successThreshold < 1, () -> "failureRatio must bigger than 0");

            this.successThreshold = successThreshold;
            return self();
        }

        public long getDelay() {
            return delay;
        }

        public ChronoUnit getDelayUnit() {
            return delayUnit;
        }

        public int getRequestVolumeThreshold() {
            return requestVolumeThreshold;
        }

        public double getFailureRatio() {
            return failureRatio;
        }

        public int getSuccessThreshold() {
            return successThreshold;
        }

        private CircuitBreakerOptions self() {
            return this;
        }

    }

}
