package com.microsphere.validation;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public abstract class AbstractBindingResult<T> implements BindingResult {

    private final String objectName;

    private final List<ObjectError> errors;

    protected AbstractBindingResult(String objectName) {
        this.objectName = objectName;
        this.errors = new LinkedList<>();
    }

    public abstract T getTarget();

    @Override
    public String getObjectName() {
        return this.objectName;
    }

    @Override
    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }

    @Override
    public boolean hasFieldErrors() {
        return !this.errors.isEmpty();
    }

    @Override
    public FieldError getFirstFieldError() {
        if (hasFieldErrors()) {
            return null;
        }

        for (ObjectError error : this.errors) {
            if (error instanceof FieldError) {
                return (FieldError) error;
            }
        }
        return null;
    }

    @Override
    public Collection<FieldError> getFieldErrors() {
        if (!hasFieldErrors()) {
            return List.of();
        }

        final List<FieldError> errors = new LinkedList<>();
        for (ObjectError error : this.errors) {
            if (error instanceof FieldError) {
                errors.add((FieldError) error);
            }
        }
        return List.copyOf(errors);
    }

    @Override
    public void reject(String errorCode) {
        this.reject(errorCode, null);
    }

    @Override
    public void reject(String errorCode, String defaultMessage) {
        this.errors.add(new ObjectError(getObjectName(), errorCode, defaultMessage));
    }

    @Override
    public void rejectValue(String field, String errorCode) {
        this.rejectValue(field, errorCode, null);
    }

    @Override
    public void rejectValue(String field, String errorCode, String defaultMessage) {
        this.errors.add(new FieldError(getObjectName(), field, errorCode, defaultMessage));
    }

}
