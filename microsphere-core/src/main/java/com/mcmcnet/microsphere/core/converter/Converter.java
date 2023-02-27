package com.mcmcnet.microsphere.core.converter;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public interface Converter<T, R> {

    R convert(T source);
}
