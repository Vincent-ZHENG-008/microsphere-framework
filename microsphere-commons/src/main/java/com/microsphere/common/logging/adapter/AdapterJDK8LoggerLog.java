package com.microsphere.common.logging.adapter;

import com.microsphere.common.logging.Log;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class AdapterJDK8LoggerLog implements Log {

    private final Logger delegate;

    public AdapterJDK8LoggerLog(Class<?> clazz) {
        this.delegate = Logger.getLogger(clazz.getName());
    }


    @Override
    public boolean isErrorEnabled() {
        return delegate.isLoggable(Level.SEVERE);
    }

    @Override
    public boolean isWarnEnabled() {
        return delegate.isLoggable(Level.WARNING);
    }

    @Override
    public boolean isInfoEnabled() {
        return delegate.isLoggable(Level.INFO);
    }

    @Override
    public boolean isDebugEnabled() {
        return delegate.isLoggable(Level.FINE);
    }

    @Override
    public boolean isTraceEnabled() {
        return delegate.isLoggable(Level.FINER);
    }

    @Override
    public void error(String message, Object... parameters) {
        delegate.log(Level.SEVERE, message, parameters);
    }

    @Override
    public void error(String message, Throwable err) {
        delegate.log(Level.SEVERE, message, err);
    }

    @Override
    public void warn(String message, Object... parameters) {
        delegate.log(Level.WARNING, message, parameters);
    }

    @Override
    public void warn(String message, Throwable err) {
        delegate.log(Level.WARNING, message, err);
    }

    @Override
    public void info(String message, Object... parameters) {
        delegate.log(Level.INFO, message, parameters);
    }

    @Override
    public void debug(String message, Object... parameters) {
        delegate.log(Level.FINE, message, parameters);
    }

    @Override
    public void trace(String message, Object... parameters) {
        delegate.log(Level.FINER, message, parameters);
    }
}
