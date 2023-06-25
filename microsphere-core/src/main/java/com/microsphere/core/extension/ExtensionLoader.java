package com.microsphere.core.extension;

import com.microsphere.core.context.Lifecycle;
import com.microsphere.core.util.Assert;
import com.microsphere.core.util.ReflectionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public final class ExtensionLoader<T> {

    private static final LoadingStrategy[] strategies = loadLoadingStrategies();

    private final Map<String, Object> singletons = new ConcurrentHashMap<>();

    private final Map<String, ComponentFactory> factories = new ConcurrentHashMap<>();

    private final Class<T> type;

    public ExtensionLoader(Class<T> type) {
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    public T load(String name) {
        if (!Assert.hasText(name)) {
            throw new IllegalArgumentException("name cannot be null");
        }

        Object instance = singletons.get(name);
        if (instance == null) {
            instance = singletons.computeIfAbsent(name, k -> {
                final ComponentFactory factory = loadFactory(name);
                if (factory == null) {
                    throw new NullPointerException("cannot find component factory");
                }

                // judge component implement Lifecycle interface
                final Object component = factory.getObject();
                if (component instanceof Lifecycle) {
                    final Lifecycle lifecycle = (Lifecycle) component;
                    if (!lifecycle.isRunning()) {
                        lifecycle.start();
                    }
                }

                return component;
            });
        }

        return (T) instance;
    }

    private ComponentFactory loadFactory(String name) {
        ComponentFactory factory = this.factories.get(name);
        if (factory != null) {
            return factory;
        }

        synchronized (factories) {
            factory = this.factories.get(name);
            if (factory != null) {
                return factory;
            }

            for (LoadingStrategy strategy : strategies) {
                final ClassLoader classLoader = this.getClass().getClassLoader();
                final String fileName = strategy.directory() + this.type.getName();
                final URL url;
                if ((url = classLoader.getResource(fileName)) == null) {
                    continue;
                }

                try {
                    final Properties resources;
                    if (!Assert.isEmpty((resources = loadResourceContent(url)))) {
                        resources.forEach((key, value) -> factories.putIfAbsent((String) key, () -> {
                            try {
                                return ReflectionUtils.newInstance((Class<?>) value);
                            } catch (Exception e) {
                                throw new RuntimeException("Cannot instance resource", e);
                            }
                        }));
                    }
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex.getMessage(), ex);
                }
            }

            factory = this.factories.get(name);
        }
        return factory;
    }

    private static LoadingStrategy[] loadLoadingStrategies() {
        return ServiceLoader.load(LoadingStrategy.class).stream()
                .map(ServiceLoader.Provider::get)
                .toArray(LoadingStrategy[]::new);
    }

    private static Properties loadResourceContent(URL url) throws ClassNotFoundException {
        final Properties prop = new Properties();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
            String line;
            while (Assert.hasText((line = reader.readLine()))) {
                final int split = line.indexOf("=");
                if (split < 0) {
                    continue;
                }

                final String name;
                if (split > 0) {
                    name = line.substring(0, split).strip();
                } else {
                    name = LoadingStrategy.DEFAULT_NAME;
                }

                final String className = line.substring(split + 1).strip();
                prop.put(name, Class.forName(className));
            }

            return prop;
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

}
