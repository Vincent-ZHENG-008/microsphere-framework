package com.microsphere.common.event;

import com.microsphere.common.logging.Log;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
class EventPublisherTest {

    private final Log log = Log.getFactory(getClass());

    @Test
    void publish() {
        final EventPublisher<String> eventPublisher = EventPublisher.processor(String.class)
                .onEvent(log::info);

        eventPublisher.publish("HelloWorld");
    }

    @Test
    void parallelPublish() {
        final EventPublisher<String> eventPublisher = EventPublisher.parallelProcessor(String.class, Executors.newSingleThreadExecutor());
        eventPublisher.onEvent(ms -> log.info("threadId: " + Thread.currentThread().getName() + " content: " + ms));

        eventPublisher.publish("HelloWorld");
    }

}