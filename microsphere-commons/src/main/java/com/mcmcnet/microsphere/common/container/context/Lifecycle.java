package com.mcmcnet.microsphere.common.container.context;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public interface Lifecycle {

    void start();

    void stop();

    boolean isRunning();

}
