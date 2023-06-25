package com.microsphere.core.extension.support;

import com.microsphere.core.extension.ExtensionAccessor;
import com.microsphere.core.extension.ExtensionLoader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class FrameworkModel implements ExtensionAccessor {

    private final Map<Class<?>, ExtensionLoader<?>> extensionLoaders = new ConcurrentHashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Extension type must not be null");
        }
        if (!type.isInterface()) {
            throw new IllegalArgumentException("Extension type must be an interface");
        }

        ExtensionLoader<?> extensionLoader = extensionLoaders.get(type);
        if (extensionLoader == null) {
            extensionLoaders.putIfAbsent(type, new ExtensionLoader<>(type));

            extensionLoader = extensionLoaders.get(type);
        }

        return (ExtensionLoader<T>) extensionLoader;
    }
}
