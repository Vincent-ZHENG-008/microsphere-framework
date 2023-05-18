package com.microsphere.validation.bean;

import com.microsphere.common.code.export.internal.InternalCode;
import com.microsphere.common.container.context.Lifecycle;
import com.microsphere.core.util.Assert;
import com.microsphere.validation.AbstractBindingResult;
import com.microsphere.validation.FieldError;
import jakarta.validation.*;

import java.util.Collection;
import java.util.Set;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public class BeanPropertyValidationExtension<T> extends AbstractBindingResult<T> implements Lifecycle {

    private static final String VALIDATOR_FACTORY_ERROR = "ValidatorFactory cannot be null";

    private static final String DELEGATE_BINDING_RESULT_ERROR = "BeanPropertyBindingResult cannot be null";

    private final BeanPropertyBindingResult<T> delegate;

    private final ValidatorFactory validatorFactory;

    private final Class<?>[] groups;

    protected BeanPropertyValidationExtension(BeanPropertyBindingResult<T> delegate, Class<?>... groups) {
        this(delegate, loadDefaultValidatorSpi(), groups);
    }

    protected BeanPropertyValidationExtension(BeanPropertyBindingResult<T> delegate, ValidatorFactory validatorFactory, Class<?>... groups) {
        super(Assert.notNull(delegate, DELEGATE_BINDING_RESULT_ERROR).getObjectName());
        this.delegate = delegate;
        this.validatorFactory = Assert.notNull(validatorFactory, VALIDATOR_FACTORY_ERROR);
        this.groups = groups;
        this.start();
    }

    @Override
    public T getTarget() {
        return delegate.getTarget();
    }

    @Override
    public boolean hasErrors() {
        return this.delegate.hasErrors();
    }

    @Override
    public void start() {
        final Validator validator = this.validatorFactory.getValidator();
        final Set<ConstraintViolation<Object>> violations = validator.validate(getTarget(), this.groups);

        for (ConstraintViolation<Object> violation : violations) {
            rejectValue(violation.getPropertyPath().toString(), InternalCode.BAD_PARAMETER.code(), violation.getMessage());
        }
    }

    @Override
    public void stop() {
        this.validatorFactory.close();
    }

    @Override
    public boolean isRunning() {
        return true;
    }

    @Override
    public String getObjectName() {
        return this.delegate.getObjectName();
    }

    @Override
    public boolean hasFieldErrors() {
        return this.delegate.hasFieldErrors();
    }

    @Override
    public FieldError getFirstFieldError() {
        return this.delegate.getFirstFieldError();
    }

    @Override
    public Collection<FieldError> getFieldErrors() {
        return this.delegate.getFieldErrors();
    }

    @Override
    public void reject(String errorCode) {
        this.delegate.reject(errorCode);
    }

    @Override
    public void reject(String errorCode, String defaultMessage) {
        this.delegate.reject(errorCode, defaultMessage);
    }

    @Override
    public void rejectValue(String field, String errorCode) {
        this.delegate.rejectValue(field, errorCode);
    }

    @Override
    public void rejectValue(String field, String errorCode, String defaultMessage) {
        this.delegate.rejectValue(field, errorCode, defaultMessage);
    }

    private static ValidatorFactory loadDefaultValidatorSpi() {
        return Validation.byDefaultProvider()
                .configure()
                .buildValidatorFactory();
    }
}
