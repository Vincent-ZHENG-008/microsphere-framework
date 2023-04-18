package com.microsphere.common.code.export.internal.error;

import com.microsphere.common.code.export.Code;

/**
 * Service Internal Error Exception
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 **/
public class ServiceInternalErrorCode implements Code {

    @Override
    public String code() {
        return "B0001";
    }

    @Override
    public String message() {
        return "service internal error";
    }
}
