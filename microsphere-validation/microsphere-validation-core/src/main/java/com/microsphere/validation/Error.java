package com.microsphere.validation;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public interface Error {

    boolean hasErrors();

    void reject(String errorCode);

    void reject(String errorCode, String defaultMessage);

}
