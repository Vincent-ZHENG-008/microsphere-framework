package com.microsphere.faulttolerance;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public interface Bulkhead {

    class BulkheadOptions {

        /**
         * @see org.eclipse.microprofile.faulttolerance.Bulkhead.value
         */
        private int concurrentCalls = 10;

        /**
         * @see org.eclipse.microprofile.faulttolerance.Bulkhead.waitingTaskQueue
         */
        private int waitingTaskQueue = 10;

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
