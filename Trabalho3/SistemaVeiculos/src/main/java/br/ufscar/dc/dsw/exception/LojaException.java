package br.ufscar.dc.dsw.exception;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

public class LojaException extends RuntimeException {

    private static final long serialVersionUID = -5662500638664048250L;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private final HttpStatus statusCode;
    private final String statusText;
    private final HttpHeaders headers;
    private final byte[] body;
    private final transient Charset charset;

    public LojaException(HttpStatus statusCode, String statusText, @Nullable HttpHeaders headers, @Nullable byte[] body, @Nullable Charset charset) {
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.headers = headers;
        this.body = body;
        this.charset = charset;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    public Charset getCharset() {
        return charset;
    }

    public String getResponseBodyAsString() {
        return getResponseBodyAsString(DEFAULT_CHARSET);
    }

    public String getResponseBodyAsString(Charset fallbackCharset) {
        if (this.charset == null) {
            return new String(this.body, fallbackCharset);
        }
        return new String(this.body, this.charset);
    }

}