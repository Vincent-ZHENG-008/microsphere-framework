package com.mcmcnet.microsphere.common.code.export.internal.error;

import com.mcmcnet.microsphere.common.code.export.Code;

/**
 * todo...
 *
 * @author zhengwenhuan@gdmcmc.cn
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
