package com.microsphere.common.code.export.internal.error;

import com.microsphere.common.code.export.Code;

/**
 * client repeat request exception
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 **/
public class ClientRequestRepeatCode implements Code {

    @Override
    public String code() {
        return "A0200";
    }

    @Override
    public String message() {
        return "client request repeat";
    }
}
