package com.mcmcnet.microsphere.common.container.context;

/**
 * todo...
 *
 * @author zhengwenhuan@gdmcmc.cn
 * @since todo...
 **/
public interface Lifecycle {

    void start();

    void stop();

    boolean isRunning();

}
