package com.mcmcnet.microsphere.common.logging;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public interface Log {

    boolean isErrorEnabled();

    boolean isWarnEnabled();

    boolean isInfoEnabled();

    boolean isDebugEnabled();

    boolean isTraceEnabled();

    void error(String message);

    void error(String message, Object... parameters);

    void error(String message, Throwable err);

    void warn(String message, Object... parameters);

    void warn(String message, Throwable err);

    void info(String message, Object... parameters);

    void debug(String message, Object... parameters);

    void trace(String message, Object... parameters);

    static Log getFactory(Class<?> type) {
        return new LogFactory(type);
    }

}

