package com.microsphere.common.code.export.internal;

import com.microsphere.common.code.export.Code;

/**
 * Request Success Code
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 **/
public class SuccessCode implements Code {

    @Override
    public String code() {
        return "00000";
    }

    @Override
    public String message() {
        return "request success";
    }
}
