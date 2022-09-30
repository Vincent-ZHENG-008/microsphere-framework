package com.mcmcnet.microsphere.common.serviceloader.inject;

import com.mcmcnet.microsphere.common.serviceloader.ExtensionAccessor;
import com.mcmcnet.microsphere.common.serviceloader.ExtensionFactory;
import com.mcmcnet.microsphere.common.serviceloader.ExtensionLoader;
import com.mcmcnet.microsphere.common.serviceloader.SPI;

/**
 * @author zhengwenhuan@gdmcmc.cn
 */
public class SpiExtensionInject implements ExtensionFactory {

    private ExtensionAccessor extensionAccessor;

    @Override
    public void setExtensionAccessor(ExtensionAccessor extensionAccessor) {
        this.extensionAccessor = extensionAccessor;
    }

    @Override
    public <T> T getInstance(Class<T> type, String name) {
        if (type.isInterface() && type.isAnnotationPresent(SPI.class)) {
            final ExtensionLoader<T> loader = extensionAccessor.getExtensionLoad(type);
            if (!loader.getSupportedExtensions().isEmpty()) {
                return loader.getAdaptiveExtension();
            }
        }

        return null;
    }
}
