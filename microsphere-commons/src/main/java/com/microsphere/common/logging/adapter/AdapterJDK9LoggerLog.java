package com.microsphere.common.logging.adapter;

import com.microsphere.common.logging.Log;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public class AdapterJDK9LoggerLog implements Log {

    private final System.Logger delegate;

    public AdapterJDK9LoggerLog(Class<?> clazz) {
        this.delegate = System.getLogger(clazz.getName());
    }

    @Override
    public boolean isErrorEnabled() {
        return delegate.isLoggable(System.Logger.Level.ERROR);
    }

    @Override
    public boolean isWarnEnabled() {
        return delegate.isLoggable(System.Logger.Level.WARNING);
    }

    @Override
    public boolean isInfoEnabled() {
        return delegate.isLoggable(System.Logger.Level.INFO);
    }

    @Override
    public boolean isDebugEnabled() {
        return delegate.isLoggable(System.Logger.Level.DEBUG);
    }

    @Override
    public boolean isTraceEnabled() {
        return delegate.isLoggable(System.Logger.Level.TRACE);
    }

    @Override
    public void error(String message, Object... parameters) {
        delegate.log(System.Logger.Level.ERROR, message, parameters);
    }

    @Override
    public void error(String message, Throwable err) {
        delegate.log(System.Logger.Level.ERROR, message, err);
    }

    @Override
    public void warn(String message, Object... parameters) {
        delegate.log(System.Logger.Level.WARNING, message, parameters);
    }

    @Override
    public void warn(String message, Throwable err) {
        delegate.log(System.Logger.Level.WARNING, message, err);
    }

    @Override
    public void info(String message, Object... parameters) {
        delegate.log(System.Logger.Level.INFO, message, parameters);
    }

    @Override
    public void debug(String message, Object... parameters) {
        delegate.log(System.Logger.Level.DEBUG, message, parameters);
    }

    @Override
    public void trace(String message, Object... parameters) {
        delegate.log(System.Logger.Level.TRACE, message, parameters);
    }
}
