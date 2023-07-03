package com.microsphere.statemachine.exception;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class MachineClosedException extends RuntimeException {

    private static final String MESSAGE = "Statemachine was closed.";

    public static final MachineClosedException GLOBAL = new MachineClosedException(MESSAGE);

    public MachineClosedException(String message) {
        super(message, null, true, false);
    }

}
