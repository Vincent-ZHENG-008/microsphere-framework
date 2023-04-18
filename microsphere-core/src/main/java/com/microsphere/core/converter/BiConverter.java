package com.microsphere.core.converter;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 **/
public interface BiConverter<T1, T2, R> {

    R convert(T1 source1, T2 source2);
}
