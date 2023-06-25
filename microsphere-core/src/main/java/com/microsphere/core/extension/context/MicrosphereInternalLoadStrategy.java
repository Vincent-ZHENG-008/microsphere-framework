package com.microsphere.core.extension.context;

import com.microsphere.core.extension.LoadingStrategy;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class MicrosphereInternalLoadStrategy implements LoadingStrategy {

    private static final String DIRECTORY = "META-INF/microsphere.internal/";

    @Override
    public String directory() {
        return DIRECTORY;
    }

    @Override
    public String getName() {
        return "MICROSPHERE_INTERNAL";
    }
}
