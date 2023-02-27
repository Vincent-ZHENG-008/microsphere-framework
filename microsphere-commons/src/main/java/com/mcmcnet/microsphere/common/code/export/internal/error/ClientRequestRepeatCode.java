package com.mcmcnet.microsphere.common.code.export.internal.error;

import com.mcmcnet.microsphere.common.code.export.Code;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
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
