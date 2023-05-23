package com.microsphere.common.event;

import java.util.EventListener;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public interface EventConsumer<T> extends EventListener {

    void accept(T event);

}
