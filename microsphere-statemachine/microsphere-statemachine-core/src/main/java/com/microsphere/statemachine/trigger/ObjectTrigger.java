package com.microsphere.statemachine.trigger;

import com.microsphere.core.util.Assert;

import java.util.Objects;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public class ObjectTrigger<S, E> implements Trigger<S, E> {

    private final E event;

    public ObjectTrigger(E event) {
        this.event = event;
    }

    @Override
    public boolean evaluate(TriggerContext<S, E> ctx) {
        Assert.notNull(ctx, () -> "TriggerContext cannot be null.");

        return Objects.equals(event, ctx.getEvent());
    }

    @Override
    public E getEvent() {
        return event;
    }
}
