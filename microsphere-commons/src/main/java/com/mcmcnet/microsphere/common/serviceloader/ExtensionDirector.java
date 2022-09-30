package com.mcmcnet.microsphere.common.serviceloader;

import com.mcmcnet.angel.commons.container.model.ScopeModel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author zhengwenhuan@gdmcmc.cn
 */
public final class ExtensionDirector implements ExtensionAccessor {

    private final ConcurrentMap<Class<?>, ExtensionLoader<?>> extensionLoadersMap = new ConcurrentHashMap<>();

    private final ScopeModel scopeModel;

    public ExtensionDirector(ScopeModel scopeModel) {
        this.scopeModel = scopeModel;
    }

    @Override
    public ExtensionDirector getExtensionDirector() {
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> ExtensionLoader<T> getExtensionLoad(Class<T> type) {
        // find in local cache
        ExtensionLoader<T> loader = (ExtensionLoader<T>) extensionLoadersMap.get(type);
        if (loader == null) {
            loader = new ExtensionLoader<>(type, this, scopeModel);

            extensionLoadersMap.putIfAbsent(type, loader);
        }

        return loader;
    }
}
