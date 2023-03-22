package com.microsphere.statemachine.trigger;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public interface Trigger<S, E> {

    boolean evaluate(TriggerContext<S, E> ctx);

    E getEvent();

}
