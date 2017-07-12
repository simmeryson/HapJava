package com.guok.hap.impl.responses;/**
 * Created by guokai on 30/06/2017.
 */

import com.guok.hap.impl.http.AbstractHttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * A general error response could become all type of error response.
 *
 * @author guokai
 */
public class GeneralErrorResponse extends AbstractHttpResponse {

    private final ByteBuffer body;
    private final int statusCode;

    /**
     * Simplest error response receiving {@link HttpStatusCodes}
     */
    public GeneralErrorResponse(int statusCode) throws IOException {
        this(statusCode, (JsonObject) null);
    }

    /**
     * Constructor receiving ont pair param of {@link HttpStatusCodes} and {@link HapStatusCodes}
     *
     * @param statusCode    {@link HttpStatusCodes}
     * @param hapStatusCode {@link HapStatusCodes}
     */
    public GeneralErrorResponse(int statusCode, int hapStatusCode) throws IOException {
        this(statusCode, Collections.singletonMap("status", hapStatusCode));
    }

    /**
     * Constructor of general error response receive all type params;
     *
     * @param statusCode {@link HttpStatusCodes}
     * @param params     json paras, could be iid, aid and {@link HapStatusCodes}
     */
    public GeneralErrorResponse(int statusCode, Map<String, Integer> params) throws IOException {
        if (statusCode < 100 || statusCode > 600) {
            throw new IllegalArgumentException("HttpStatusCode is not from HttpStatusCode.class!");
        }

        this.statusCode = statusCode;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            if (params != null) {
                JsonObjectBuilder builder = Json.createObjectBuilder();
                for (Map.Entry<String, Integer> entry : params.entrySet()) {
                    builder.add(entry.getKey(), entry.getValue());
                }
                Json.createWriter(baos).write(builder.build());
            }
            this.body = baos.size() > 0 ? ByteBuffer.wrap(baos.toByteArray()) : ByteBuffer.allocate(0);
        }
    }

    /**
     * Constructor of general error response receive all type params;
     *
     * @param statusCode {@link HttpStatusCodes}
     * @param json parameters in json object
     * @throws IOException
     */
    public GeneralErrorResponse(int statusCode, JsonObject json) throws IOException {
        if (statusCode < 100 || statusCode > 600) {
            throw new IllegalArgumentException("HttpStatusCode is not from HttpStatusCode.class!");
        }

        this.statusCode = statusCode;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            if (json != null) {
                Json.createWriter(baos).write(json);
            }
            this.body = baos.size() > 0 ? ByteBuffer.wrap(baos.toByteArray()) : ByteBuffer.allocate(0);
        }
    }

    @Override
    public int getStatusCode() {
        return this.statusCode;
    }

    @Override
    public ByteBuffer getBody() {
        return body;
    }
}
