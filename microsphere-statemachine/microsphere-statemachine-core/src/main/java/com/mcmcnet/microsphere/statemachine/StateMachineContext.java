package com.mcmcnet.microsphere.statemachine;

import java.util.Map;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public interface StateMachineContext<S, E> {

    String getId();

    S getState();

    E getEvent();

    Map<String, Object> getEventHeaders();

}
