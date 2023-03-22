package com.microsphere.common.code.export.internal.error;

import com.microsphere.common.code.export.Code;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public class ClientBadParameterCode implements Code {

    @Override
    public String code() {
        return "A0100";
    }

    @Override
    public String message() {
        return "client bad parameter";
    }
}
