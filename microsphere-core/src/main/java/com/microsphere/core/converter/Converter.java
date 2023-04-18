package com.microsphere.core.converter;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 **/
public interface Converter<T, R> {

    R convert(T source);
}
