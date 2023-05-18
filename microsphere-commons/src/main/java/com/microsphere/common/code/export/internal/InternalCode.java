package com.microsphere.common.code.export.internal;

import com.microsphere.common.code.export.internal.error.ClientBadParameterCode;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public interface InternalCode {

    SuccessCode SUCCESS = new SuccessCode();

    ClientBadParameterCode BAD_PARAMETER = new ClientBadParameterCode();

}
