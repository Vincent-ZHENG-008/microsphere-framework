package com.mcmcnet.microsphere.statemachine.trigger;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public interface Trigger<S, E> {

    boolean evaluate(TriggerContext<S, E> ctx);

    E getEvent();

}
