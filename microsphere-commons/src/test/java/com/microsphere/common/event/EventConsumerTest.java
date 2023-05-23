package com.microsphere.common.event;

import org.junit.jupiter.api.Test;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
class EventConsumerTest {

    @Test
    void accept() {
        EventConsumer<String> consumer = event -> System.out.printf("consumer accept event: %s \n", event);

        consumer.accept("hello world");
    }
}