package com.microsphere.common.code.export;

/**
 * Interface of response code
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 **/
public interface Code {

    /**
     * Response Code
     *
     * @return String of code
     */
    String code();

    /**
     * Message of content
     */
    String message();

}
