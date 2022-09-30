package com.mcmcnet.microsphere.common.serviceloader;

import com.mcmcnet.angel.commons.container.context.Lifecycle;
import com.mcmcnet.angel.commons.container.model.ScopeModel;
import com.mcmcnet.angel.commons.util.CollectionUtils;
import com.mcmcnet.angel.commons.util.Holder;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.StreamSupport;

/**
 * @author zhengwenhuan@gdmcmc.cn
 */
public final class ExtensionLoader<T> {

    private static final LoadingStrategy[] strategies = loadLoadingStrategies();

    private static final ConcurrentMap<Class<?>, ExtensionLoader<?>> EXTENSION_LOADER = new ConcurrentHashMap<>(32);

    private static final ConcurrentMap<Class<?>, Object> EXTENSION_INSTANCES = new ConcurrentHashMap<>(64);

    private final Class<T> type;

    private final ExtensionFactory extensionFactory;

    private final ConcurrentMap<Class<?>, String> cachedNames = new ConcurrentHashMap<>();

    private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();

    private final ConcurrentMap<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<>(16);

    private final Holder<Object> cachedAdaptiveInstance = new Holder<>();

    private Class<?> cachedAdaptiveClass;

    private final ExtensionDirector extensionDirector;

    private final ScopeModel scopeModel;

    public ExtensionLoader(Class<T> type, ExtensionDirector extensionDirector, ScopeModel scopeModel) {
        this.type = type;
        this.extensionDirector = extensionDirector;
        this.scopeModel = scopeModel;

        this.extensionFactory = (type == ExtensionFactory.class ? null : this.extensionDirector.getExtensionLoad(ExtensionFactory.class).getAdaptiveExtension());
    }

    @SuppressWarnings("unchecked")
    public T getExtension(String name) {
        Holder<Object> holder = cachedInstances.get(name);
        if (holder == null) {
            cachedInstances.putIfAbsent(name, new Holder<>());
            holder = cachedInstances.get(name);
        }

        Object instance = holder.get();
        if (instance == null) {
            synchronized (holder) {
                instance = createExtension(name);
                holder.set(instance);
            }
        }

        return (T) instance;
    }

    @SuppressWarnings("unchecked")
    public T getAdaptiveExtension() {
        Object instance = cachedAdaptiveInstance.get();
        if (instance == null) {
            synchronized (cachedAdaptiveInstance) {
                instance = cachedAdaptiveInstance.get();
                if (instance == null) {
                    try {
                        instance = createAdaptiveExtension();
                        cachedAdaptiveInstance.set(instance);
                    } catch (Throwable t) {
                        throw new IllegalStateException("Failed to create adaptive instance: " + t, t);
                    }
                }
            }
        }

        return (T) instance;
    }

    public Set<String> getSupportedExtensions() {
        return Set.copyOf(getExtensionClasses().keySet());
    }

    @SuppressWarnings("unchecked")
    private T createExtension(String name) {
        final Class<?> clazz = getExtensionClasses().get(name);
        if (clazz == null) {
            throw new RuntimeException();
        }

        try {
            T instance = (T) EXTENSION_LOADER.get(clazz);
            if (instance == null) {
                EXTENSION_INSTANCES.putIfAbsent(clazz, clazz.getDeclaredConstructor().newInstance());
                instance = (T) EXTENSION_INSTANCES.get(clazz);
                injectExtension(instance);

                if (instance instanceof Lifecycle lifecycle) {
                    lifecycle.initialize();
                }
            }

            return instance;
        } catch (Exception exception) {
            exception.printStackTrace();

            throw new RuntimeException(exception);
        }
    }

    private Map<String, Class<?>> loadExtensionClasses() {

        final Map<String, Class<?>> extensionClasses = new HashMap<>();
        for (final LoadingStrategy strategy : strategies) {
            loadDirectory(extensionClasses, strategy, type.getName());
        }

        return extensionClasses;
    }

    private void loadDirectory(Map<String, Class<?>> extensionClasses, LoadingStrategy strategy, String type) {
        final String directory = strategy.directory();
        final String fileName = directory + type;

        try {
            Enumeration<URL> urls;

            final Set<ClassLoader> classLoaders = scopeModel.getClassLoaders();
            if (CollectionUtils.isEmpty(classLoaders)) {
                urls = ClassLoader.getSystemResources(fileName);
                loadFromClass(extensionClasses, urls, null);
            } else {
                for (final ClassLoader classLoader : classLoaders) {
                    urls = classLoader.getResources(fileName);
                    loadFromClass(extensionClasses, urls, classLoader);
                }
            }


        } catch (Exception exception) {
            // todo
        }
    }

    private void loadFromClass(Map<String, Class<?>> extensionClasses, Enumeration<URL> urls, ClassLoader classLoader) {
        if (urls != null) {
            while (urls.hasMoreElements()) {
                final URL url = urls.nextElement();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
                    String line;
                    String clazz;

                    while (StringUtils.isNotBlank((line = reader.readLine()))) {
                        final int ci = line.indexOf('#');
                        if (ci >= 0) {
                            continue;
                        }

                        if (line.length() > 0) {
                            String name = null;
                            final int i = line.indexOf('=');
                            if (i > 0) {
                                name = line.substring(0, i).trim();
                                clazz = line.substring(i + 1).trim();
                            } else {
                                clazz = line;
                            }

                            if (StringUtils.isNotBlank(clazz)) {
                                loadClass(extensionClasses, url, Class.forName(clazz, true, classLoader), name);
                            }
                        }
                    }
                } catch (IOException exception) {
                    // todo
                } catch (ClassNotFoundException exception) {
                    // todo
                }
            }
        }
    }

    private void loadClass(Map<String, Class<?>> extensionClasses, URL url, Class<?> clazz, String name) {
        if (!cachedNames.containsKey(clazz)) {
            cachedNames.put(clazz, name);
        }
        if (clazz.isAnnotationPresent(Adaptive.class)) {
            if (cachedAdaptiveClass == null) {
                cachedAdaptiveClass = clazz;
            }
        } else {
            Class<?> c = extensionClasses.get(name);
            if (c == null) {
                extensionClasses.put(name, clazz);
            } else if (c != clazz) {
                // log err: todo
            }
        }
    }

    @SuppressWarnings("unchecked")
    private T createAdaptiveExtension() {
        final Class<?> adaptiveExtensionClass = getAdaptiveExtensionClass();
        final Object instance;
        try {
            instance = adaptiveExtensionClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (extensionFactory == null) {
            return (T) instance;
        }

        return (T) instance;
    }

    private Class<?> getAdaptiveExtensionClass() {
        getExtensionClasses();
        if (cachedAdaptiveClass != null) {
            return cachedAdaptiveClass;
        }

        throw new RuntimeException();
    }

    private Map<String, Class<?>> getExtensionClasses() {
        Map<String, Class<?>> classes = cachedClasses.get();
        if (classes == null) {
            synchronized (cachedClasses) {
                classes = cachedClasses.get();
                if (classes == null) {
                    classes = loadExtensionClasses();
                    cachedClasses.set(classes);
                }
            }
        }

        return classes;
    }

    private T injectExtension(T instance) {
        if (extensionFactory == null) {
            return instance;
        }

        try {
            for (final Method method : instance.getClass().getMethods()) {
                final String methodName = method.getName();
                if (!methodName.startsWith("set") || method.getParameterCount() != 1) {
                    continue;
                }

                final Class<?> parameterType = method.getParameterTypes()[0];
                final String property = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);

                final Object target = extensionFactory.getInstance(parameterType, property);
                if (target != null) {
                    method.invoke(instance, target);
                }
            }
        } catch (Exception exception) {
            throw new RuntimeException();// todo
        }

        return instance;
    }

    private static LoadingStrategy[] loadLoadingStrategies() {
        return StreamSupport.stream(ServiceLoader.load(LoadingStrategy.class).spliterator(), false).sorted().toArray(LoadingStrategy[]::new);
    }

    private static <T> boolean withExtensionAnnotation(Class<T> type) {
        return type.isAnnotationPresent(SPI.class);
    }

}
