package com.microsphere.core.context;

/**
 * Lifecycle of component
 * <p>
 * flow chart should be start --> stop
 * </p>
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 **/
public interface Lifecycle {

    void start();

    void stop();

    boolean isRunning();

}
