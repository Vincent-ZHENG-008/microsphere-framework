package com.microsphere.common.code.export.internal.error;

import com.microsphere.common.code.export.Code;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
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
