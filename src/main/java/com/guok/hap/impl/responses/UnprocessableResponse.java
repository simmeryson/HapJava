package com.guok.hap.impl.responses;


import com.guok.hap.impl.http.AbstractHttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.json.Json;

/**
 * For a well-formed request that contains invalid parameters;
 *
 * @author guokai
 */
public class UnprocessableResponse extends AbstractHttpResponse {

    private final ByteBuffer body;

    public UnprocessableResponse(int errorNum) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Json.createWriter(baos).write(Json.createObjectBuilder().add("status", errorNum).build());
            this.body = ByteBuffer.wrap(baos.toByteArray());
        }
    }

    @Override
    public int getStatusCode() {
        return 422;
    }

    @Override
    public ByteBuffer getBody() {
        return body;
    }
}
