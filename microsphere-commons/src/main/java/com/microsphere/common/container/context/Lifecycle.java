package com.microsphere.common.container.context;

/**
 * Lifecycle of bean
 *
 * flow chart should be start --> stop
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 **/
public interface Lifecycle {

    void start();

    void stop();

    boolean isRunning();

}
