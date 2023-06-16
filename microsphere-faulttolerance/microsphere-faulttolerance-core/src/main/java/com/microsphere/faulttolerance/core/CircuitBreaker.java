package com.microsphere.faulttolerance.core;

import com.microsphere.core.util.Assert;

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
public interface CircuitBreaker extends ProxyCallable {

    class CircuitBreakerOptions {

        /**
         * @see org.eclipse.microprofile.faulttolerance.CircuitBreaker.failOn
         */
        @SuppressWarnings("unchecked")
        private Class<? extends Throwable>[] failOn = new Class[]{Throwable.class};

        /**
         * @see org.eclipse.microprofile.faulttolerance.CircuitBreaker.skipOn
         */
        @SuppressWarnings("unchecked")
        private Class<? extends Throwable>[] skipOn = new Class[]{};

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

        @SuppressWarnings("unchecked")
        public CircuitBreakerOptions(Map<String, Object> prop) {
            Optional.ofNullable(prop.get("failOn")).ifPresent(val -> failOn((Class<? extends Throwable>[]) val));
            Optional.ofNullable(prop.get("skipOn")).ifPresent(val -> skipOn((Class<? extends Throwable>[]) val));
            Optional.ofNullable(prop.get("delay")).ifPresent(val -> delay((long) val));
            Optional.ofNullable(prop.get("delayUnit")).ifPresent(val -> delayUnit((ChronoUnit) val));
            Optional.ofNullable(prop.get("requestVolumeThreshold")).ifPresent(val -> requestVolumeThreshold((int) val));
            Optional.ofNullable(prop.get("failureRatio")).ifPresent(val -> failureRatio((double) val));
            Optional.ofNullable(prop.get("successThreshold")).ifPresent(val -> successThreshold((int) val));
        }

        public CircuitBreakerOptions failOn(Class<? extends Throwable>[] failOn) {
            if (Assert.isEmpty(failOn)) {
                return this;
            }

            this.failOn = Arrays.copyOf(failOn, failOn.length);
            return this;
        }

        public CircuitBreakerOptions skipOn(Class<? extends Throwable>[] skipOn) {
            if (Assert.isEmpty(skipOn)) {
                return this;
            }

            this.skipOn = Arrays.copyOf(skipOn, skipOn.length);
            return this;
        }

        public CircuitBreakerOptions delay(long delay) {
            Assert.isTrue(delay > 0, () -> "delay must bigger than 1000");

            this.delay = delay;
            return self();
        }

        public CircuitBreakerOptions delayUnit(ChronoUnit delayUnit) {
            Assert.notNull(delayUnit, () -> "delayUnit cannot be null");

            this.delayUnit = delayUnit;
            return self();
        }

        public CircuitBreakerOptions requestVolumeThreshold(int requestVolumeThreshold) {
            Assert.isTrue(requestVolumeThreshold >= 1, () -> "requestVolumeThreshold must bigger than 1");

            this.requestVolumeThreshold = requestVolumeThreshold;
            return self();
        }

        public CircuitBreakerOptions failureRatio(double failureRatio) {
            Assert.isTrue(failureRatio > 0, () -> "failureRatio must bigger than 0");

            this.failureRatio = failureRatio;
            return self();
        }

        public CircuitBreakerOptions successThreshold(int successThreshold) {
            Assert.isTrue(successThreshold >= 1, () -> "failureRatio must bigger than 0");

            this.successThreshold = successThreshold;
            return self();
        }

        public Class<? extends Throwable>[] getFailOn() {
            return Arrays.copyOf(failOn, failOn.length);
        }

        public Class<? extends Throwable>[] getSkipOn() {
            return Arrays.copyOf(skipOn, skipOn.length);
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
