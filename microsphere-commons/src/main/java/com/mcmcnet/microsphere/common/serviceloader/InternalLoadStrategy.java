package com.mcmcnet.microsphere.common.serviceloader;

/**
 * @author zhengwenhuan@gdmcmc.cn
 */
public class InternalLoadStrategy implements LoadingStrategy{
    @Override
    public String directory() {
        return "META-INF/angel/internal/";
    }
}
