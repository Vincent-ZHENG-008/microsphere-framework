package com.microsphere.core.extension;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public interface LoadingStrategy {

    String DEFAULT_NAME = "primary";

    String directory();

    default String[] includedPackages() {
        return null;
    }

    default String getName() {
        return this.getClass().getSimpleName();
    }

}
