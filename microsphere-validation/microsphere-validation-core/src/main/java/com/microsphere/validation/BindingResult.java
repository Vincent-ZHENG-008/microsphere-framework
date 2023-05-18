package com.microsphere.validation;

import java.util.Collection;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public interface BindingResult extends Error {

    String getObjectName();

    boolean hasFieldErrors();

    FieldError getFirstFieldError();

    Collection<FieldError> getFieldErrors();

    void rejectValue(String field, String errorCode);

    void rejectValue(String field, String errorCode, String defaultMessage);

}
