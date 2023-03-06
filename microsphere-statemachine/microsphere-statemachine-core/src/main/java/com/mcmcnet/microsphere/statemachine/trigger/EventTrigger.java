package com.mcmcnet.microsphere.statemachine.trigger;

import java.util.Objects;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class EventTrigger<S, E> implements Trigger<S, E> {

    private final E event;

    public EventTrigger(E event) {
        this.event = event;
    }

    @Override
    public boolean evaluate(TriggerContext<S, E> ctx) {
        return Objects.equals(event, ctx.getEvent());
    }

    @Override
    public E getEvent() {
        return event;
    }
}
