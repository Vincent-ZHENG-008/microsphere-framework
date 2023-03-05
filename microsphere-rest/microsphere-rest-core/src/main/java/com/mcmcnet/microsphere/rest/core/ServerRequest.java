package com.mcmcnet.microsphere.rest.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.OptionalLong;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public interface ServerRequest {

    String method();

    String path();

    Headers headers();

    <T> T body(Class<T> bodyType) throws IOException;

    Map<String, Object> attributes();

    interface Headers {

        List<Charset> acceptCharset();

        List<Locale.LanguageRange> acceptLanguage();

        List<MediaType> contentType();

        OptionalLong contentLength();

        InetSocketAddress host();

        List<String> header(String headerName);

    }

}
