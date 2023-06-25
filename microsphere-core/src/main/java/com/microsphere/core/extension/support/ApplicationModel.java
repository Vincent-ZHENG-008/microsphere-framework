package com.microsphere.core.extension.support;

import com.microsphere.core.extension.ExtensionAccessor;
import com.microsphere.core.extension.ExtensionLoader;

import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class ApplicationModel implements ExtensionAccessor {

    private static final ApplicationModel GLOBAL_INSTANCE = new ApplicationModel();

    private static final List<ExtensionAccessor> EXTENSION_ACCESSORS;

    static {
        final List<ExtensionAccessor> extensionAccessors = ServiceLoader.load(ExtensionAccessor.class).stream()
                .map(ServiceLoader.Provider::get)
                .toList();

        EXTENSION_ACCESSORS = new CopyOnWriteArrayList<>(extensionAccessors);
    }

    @Override
    public <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
        for (ExtensionAccessor accessor : EXTENSION_ACCESSORS) {
            final ExtensionLoader<T> extensionLoader = accessor.getExtensionLoader(type);
            if (extensionLoader != null) {
                return extensionLoader;
            }
        }
        return null;
    }

    public static <T> ExtensionLoader<T> get(Class<T> type) {
        return GLOBAL_INSTANCE.getExtensionLoader(type);
    }

}
