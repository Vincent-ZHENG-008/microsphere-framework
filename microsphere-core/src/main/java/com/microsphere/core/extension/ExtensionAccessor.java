package com.microsphere.core.extension;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public interface ExtensionAccessor {

    <T> ExtensionLoader<T> getExtensionLoader(Class<T> type);

}
