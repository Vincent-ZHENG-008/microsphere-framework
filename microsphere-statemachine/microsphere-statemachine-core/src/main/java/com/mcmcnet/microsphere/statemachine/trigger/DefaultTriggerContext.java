package com.mcmcnet.microsphere.statemachine.trigger;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public class DefaultTriggerContext<S, E> implements TriggerContext<S, E> {

    private final E event;

    public DefaultTriggerContext(E event) {
        this.event = event;
    }

    @Override
    public E getEvent() {
        return this.event;
    }
}
