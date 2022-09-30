package com.mcmcnet.microsphere.common.serviceloader;

/**
 * @author zhengwenhuan@gdmcmc.cn
 */
public interface ExtensionAccessor {

    ExtensionDirector getExtensionDirector();

    default <T> ExtensionLoader<T> getExtensionLoad(Class<T> type) {
        return this.getExtensionDirector().getExtensionLoad(type);
    }

}
