package com.microsphere.common.code.export.internal.error;

import com.microsphere.common.code.export.Code;

/**
 * Client Bad Request Exception
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 **/
public class ClientBadRequestCode implements Code {

    @Override
    public String code() {
        return "A0001";
    }

    @Override
    public String message() {
        return "client bad request";
    }
}
