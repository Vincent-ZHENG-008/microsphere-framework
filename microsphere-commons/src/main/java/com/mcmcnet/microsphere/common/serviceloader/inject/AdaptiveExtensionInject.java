package com.mcmcnet.microsphere.common.serviceloader.inject;

import com.mcmcnet.microsphere.common.container.context.Lifecycle;
import com.mcmcnet.microsphere.common.serviceloader.Adaptive;
import com.mcmcnet.microsphere.common.serviceloader.ExtensionAccessor;
import com.mcmcnet.microsphere.common.serviceloader.ExtensionFactory;
import com.mcmcnet.microsphere.common.serviceloader.ExtensionLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhengwenhuan@gdmcmc.cn
 */
@Adaptive
public class AdaptiveExtensionInject implements ExtensionFactory, Lifecycle {

    private List<ExtensionFactory> factories = List.of();

    private ExtensionAccessor extensionAccessor;

    public AdaptiveExtensionInject() {
    }

    @Override
    public <T> T getInstance(Class<T> type, String name) {
        for (final ExtensionFactory factory : factories) {
            final T instance = factory.getInstance(type, name);
            if (instance != null) {
                return instance;
            }
        }

        return null;
    }

    @Override
    public void start() {
        final ExtensionLoader<ExtensionFactory> loader = extensionAccessor.getExtensionLoad(ExtensionFactory.class);
        final List<ExtensionFactory> list = new ArrayList<>();
        for (final String name : loader.getSupportedExtensions()) {
            list.add(loader.getExtension(name));
        }

        this.factories = List.copyOf(list);
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return true;
    }

    @Override
    public void setExtensionAccessor(ExtensionAccessor extensionAccessor) {
        this.extensionAccessor = extensionAccessor;
    }
}
