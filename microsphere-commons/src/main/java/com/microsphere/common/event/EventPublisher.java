package com.microsphere.common.event;

import com.microsphere.core.util.Assert;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public interface EventPublisher<T> {

    String PUBLISH_EVENT_NULL_ERROR = "publish event argument cannot be null";

    EventPublisher<T> onEvent(EventConsumer<T> consumer);

    void publish(T event);

    static <T> EventPublisher<T> processor(Class<T> type) {
        return new SimpleEventProcessor<>(type);
    }

    static <T> EventPublisher<T> parallelProcessor(Class<T> type, Executor executor) {
        return new SimpleEventParallelProcessor<>(type, executor);
    }

    class SimpleEventProcessor<T> implements EventPublisher<T> {

        private final Class<T> type;

        private final List<EventConsumer<T>> eventConsumers = new LinkedList<>();

        private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

        private final Lock rLock = rwLock.readLock();

        private final Lock wLock = rwLock.writeLock();

        public SimpleEventProcessor(Class<T> type) {
            this.type = type;
        }

        @Override
        public EventPublisher<T> onEvent(EventConsumer<T> consumer) {
            wLock.lock();
            try {
                eventConsumers.add(consumer);
            } finally {
                wLock.unlock();
            }

            return this;
        }

        @Override
        public void publish(T event) {
            Assert.notNull(event, PUBLISH_EVENT_NULL_ERROR);
            Assert.isTrue(event.getClass().isAssignableFrom(type), () -> "Event class type is not match.");

            rLock.lock();
            try {
                if (!Assert.isEmpty(eventConsumers)) {
                    for (EventConsumer<T> eventConsumer : eventConsumers) {
                        onPublish(eventConsumer, event);
                    }
                }
            } finally {
                rLock.unlock();
            }
        }

        protected void onPublish(EventConsumer<T> eventConsumer, T event) {
            Assert.notNull(eventConsumer, () -> "EventConsumer cannot be null");
            Assert.notNull(event, PUBLISH_EVENT_NULL_ERROR);

            eventConsumer.accept(event);
        }

    }

    class SimpleEventParallelProcessor<T> extends SimpleEventProcessor<T> {

        private final Executor executor;

        public SimpleEventParallelProcessor(Class<T> type, Executor executor) {
            super(type);
            this.executor = executor;
        }

        @Override
        protected void onPublish(EventConsumer<T> eventConsumer, T event) {
            executor.execute(() -> super.onPublish(eventConsumer, event));
        }
    }

}
