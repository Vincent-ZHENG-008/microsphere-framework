package com.mcmcnet.microsphere.core.converter;

/**
 * todo...
 *
 * @author zhengwenhuan@gdmcmc.cn
 * @since todo...
 **/
public interface BiConverter<T1, T2, R> {

    R convert(T1 source1, T2 source2);
}
