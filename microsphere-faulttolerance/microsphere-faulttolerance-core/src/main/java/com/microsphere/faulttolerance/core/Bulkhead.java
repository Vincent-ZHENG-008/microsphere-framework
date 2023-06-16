package com.microsphere.faulttolerance.core;

import java.util.Map;
import java.util.Optional;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public interface Bulkhead extends ProxyCallable {

    class BulkheadOptions {

        /**
         * @see org.eclipse.microprofile.faulttolerance.Bulkhead.value
         */
        private int concurrentCalls = 10;

        /**
         * @see org.eclipse.microprofile.faulttolerance.Bulkhead.waitingTaskQueue
         */
        private int waitingTaskQueue = 10;

        public BulkheadOptions(Map<String, Object> prop) {
            Optional.ofNullable(prop.get("concurrentCalls")).ifPresent(val -> concurrentCalls((Integer) val));
            Optional.ofNullable(prop.get("waitingTaskQueue")).ifPresent(val -> waitingTaskQueue((Integer) val));
        }

        public BulkheadOptions concurrentCalls(int concurrentCalls) {
            this.concurrentCalls = concurrentCalls;

            return this;
        }

        public BulkheadOptions waitingTaskQueue(int waitingTaskQueue) {
            this.waitingTaskQueue = waitingTaskQueue;

            return this;
        }

        public int getConcurrentCalls() {
            return concurrentCalls;
        }

        public int getWaitingTaskQueue() {
            return waitingTaskQueue;
        }
    }

}
