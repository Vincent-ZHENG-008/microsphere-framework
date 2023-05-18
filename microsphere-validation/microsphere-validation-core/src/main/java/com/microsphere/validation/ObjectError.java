package com.microsphere.validation;

import com.microsphere.common.code.export.Code;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class ObjectError implements Code {

    private final String objectName;

    private final String code;

    private final String defaultMessage;

    public ObjectError(String objectName, String code, String defaultMessage) {
        this.objectName = objectName;
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    public String getObjectName() {
        return this.objectName;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.defaultMessage;
    }

}
