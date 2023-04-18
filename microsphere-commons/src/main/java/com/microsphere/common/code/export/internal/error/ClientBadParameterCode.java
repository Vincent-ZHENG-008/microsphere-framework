package com.microsphere.common.code.export.internal.error;

import com.microsphere.common.code.export.Code;

/**
 * Client Bad Parameter Exception
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
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
