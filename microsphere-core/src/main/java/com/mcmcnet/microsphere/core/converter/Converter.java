package com.mcmcnet.microsphere.core.converter;

/**
 * todo...
 *
 * @author zhengwenhuan@gdmcmc.cn
 * @since todo...
 **/
public interface Converter<T, R> {

    R convert(T source);
}
