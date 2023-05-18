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
        return false;
    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public void error(String message) {

    }

    @Override
    public void error(String message, Object... parameters) {

    }

    @Override
    public void error(String message, Throwable err) {

    }

    @Override
    public void warn(String message, Object... parameters) {

    }

    @Override
    public void warn(String message, Throwable err) {

    }

    @Override
    public void info(String message, Object... parameters) {

    }

    @Override
    public void debug(String message, Object... parameters) {

    }

    @Override
    public void trace(String message, Object... parameters) {

    }
}
