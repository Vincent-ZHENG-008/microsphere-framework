package com.microsphere.validation.bean;

import com.microsphere.core.util.Assert;
import com.microsphere.validation.AbstractBindingResult;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class BeanPropertyBindingResult<T> extends AbstractBindingResult<T> {

    private static final String VALIDATION_TARGET_ERROR = "Bad validation target";

    private final T target;

    protected BeanPropertyBindingResult(String objectName, T target) {
        super(objectName);
        this.target = Assert.notNull(target, VALIDATION_TARGET_ERROR);
    }

    @Override
    public T getTarget() {
        return this.target;
    }

}
