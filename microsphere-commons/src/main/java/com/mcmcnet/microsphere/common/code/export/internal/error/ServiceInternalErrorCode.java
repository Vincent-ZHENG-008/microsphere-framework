package com.mcmcnet.microsphere.common.code.export.internal.error;

import com.mcmcnet.microsphere.common.code.export.Code;

/**
 * todo...
 *
 * @author zhengwenhuan@gdmcmc.cn
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
