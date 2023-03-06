package com.mcmcnet.microsphere.common.logging.adapter;

import com.mcmcnet.microsphere.common.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class AdapterSlf4jLog implements Log {

    private final Logger log;

    public AdapterSlf4jLog(Class<?> target) {
        this.log = LoggerFactory.getLogger(target);
    }

    @Override
    public boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    @Override
    public boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    @Override
    public void error(String message) {
        log.error(message);
    }

    @Override
    public void error(String message, Object... parameters) {
        log.error(message, parameters);
    }

    @Override
    public void error(String message, Throwable err) {
        log.error(message, err);
    }

    @Override
    public void warn(String message, Object... parameters) {
        log.warn(message, parameters);
    }

    @Override
    public void warn(String message, Throwable err) {
        log.warn(message, err);
    }

    @Override
    public void info(String message, Object... parameters) {
        log.info(message, parameters);
    }

    @Override
    public void debug(String message, Object... parameters) {
        log.debug(message, parameters);
    }

    @Override
    public void trace(String message, Object... parameters) {
        log.trace(message, parameters);
    }
}

