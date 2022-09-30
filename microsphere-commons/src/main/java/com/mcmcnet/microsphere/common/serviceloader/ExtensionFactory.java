package com.mcmcnet.microsphere.common.serviceloader;

/**
 * @author zhengwenhuan@gdmcmc.cn
 */
@SPI
public interface ExtensionFactory extends ExtensionAccessorAware {

    <T> T getInstance(Class<T> type, String name);

    @Override
    default void setExtensionAccessor(ExtensionAccessor extensionAccessor) {

    }

}
