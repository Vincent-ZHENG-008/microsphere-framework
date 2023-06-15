package com.microsphere.faulttolerance.spring.retry;

import com.microsphere.faulttolerance.core.Retry;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public interface RetryFactory {

    Retry getObject(Retry.RetryOptions ops);

}
