package com.microsphere.statemachine.spring.listener;

import com.microsphere.core.util.Assert;
import com.microsphere.core.value.Params;
import org.springframework.context.ApplicationEvent;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class StateMachineFireEvent extends ApplicationEvent {

    private static final String PARAMS_MISS_ERROR = "Params argument cannot be null.";

    private final Params params;

    public StateMachineFireEvent(String event, Params params) {
        super(event);
        this.params = Assert.notNull(params, () -> PARAMS_MISS_ERROR);
    }

    public Params getParams() {
        return params;
    }
}
