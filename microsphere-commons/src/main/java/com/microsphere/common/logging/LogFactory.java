package com.microsphere.common.logging;

import com.microsphere.common.logging.adapter.AdapterJDK8LoggerLog;
import com.microsphere.common.logging.adapter.AdapterJDK9LoggerLog;
import com.microsphere.common.logging.adapter.AdapterSlf4jLog;
import com.microsphere.core.util.Assert;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOPLogger;

import java.util.Objects;
import java.util.function.Function;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 **/
class LogFactory implements Log {

    private static Function<Class<?>, Log> BRIDGE;

    private final Log adapter;

    static {
        // 类加载
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        // 检查Slf4j日志支持
        try {
            if (Objects.nonNull(classLoader.loadClass("org.slf4j.Logger"))) {
                if (!(LoggerFactory.getLogger(LogFactory.class) instanceof NOPLogger)) {
                    BRIDGE = AdapterSlf4jLog::new;
                }
            }
        } catch (ClassNotFoundException exception) {
            // ignore...
        }

        // 则默认使用JDK日志框架实现
        if (BRIDGE == null) {
            // 通过尝试加载类对象，如果无法加载则判定低于JDK9以下
            final String version = System.getProperty("java.version");
            if (Assert.hasText(version) && Integer.parseInt(version.substring(0, version.indexOf("."))) > 8) {
                // 如果基于JDK9以上的版本，则默认使用JDK9新特性System.Logger
                BRIDGE = AdapterJDK9LoggerLog::new;
            } else {
                BRIDGE = AdapterJDK8LoggerLog::new;
            }
        }
    }

    LogFactory(Class<?> type) {
        this.adapter = BRIDGE.apply(type);
    }

    @Override
    public boolean isErrorEnabled() {
        return adapter.isErrorEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return adapter.isWarnEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return adapter.isInfoEnabled();
    }

    @Override
    public boolean isDebugEnabled() {
        return adapter.isDebugEnabled();
    }

    @Override
    public boolean isTraceEnabled() {
        return adapter.isTraceEnabled();
    }

    @Override
    public void error(String message, Object... parameters) {
        adapter.error(message, parameters);
    }

    @Override
    public void error(String message, Throwable err) {
        adapter.error(message, err);
    }

    @Override
    public void warn(String message, Object... parameters) {
        adapter.warn(message, parameters);
    }

    @Override
    public void warn(String message, Throwable err) {
        adapter.warn(message, err);
    }

    @Override
    public void info(String message, Object... parameters) {
        adapter.info(message, parameters);
    }

    @Override
    public void debug(String message, Object... parameters) {
        adapter.debug(message, parameters);
    }

    @Override
    public void trace(String message, Object... parameters) {
        adapter.trace(message, parameters);
    }
}
