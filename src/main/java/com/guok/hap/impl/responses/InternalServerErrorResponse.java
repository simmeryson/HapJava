package com.guok.hap.impl.responses;

import com.guok.hap.impl.http.AbstractHttpResponse;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class InternalServerErrorResponse extends AbstractHttpResponse {

    private final Exception e;

    public InternalServerErrorResponse(Exception e) {
        this.e = e;
    }

    @Override
    public int getStatusCode() {
        return 500;
    }

    @Override
    public ByteBuffer getBody() {
        return ByteBuffer.wrap(e.getClass().getName().getBytes(StandardCharsets.UTF_8));
    }

    public Exception getException() {
        return e;
    }

}
