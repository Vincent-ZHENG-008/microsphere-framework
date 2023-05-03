package com.microsphere.validation;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class FieldError extends ObjectError {

    private final String fieldName;

    public FieldError(String objectName, String fieldName, String code, String defaultMessage) {
        super(objectName, code, defaultMessage);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

}
