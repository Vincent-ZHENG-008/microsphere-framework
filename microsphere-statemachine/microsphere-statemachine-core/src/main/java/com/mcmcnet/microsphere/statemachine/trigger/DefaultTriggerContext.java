package com.mcmcnet.microsphere.statemachine.trigger;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
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
