package com.guok.hap.impl.http;

import java.nio.ByteBuffer;
import java.util.Map;

public interface HttpResponse {

    int getStatusCode();

    ByteBuffer getBody();


    HttpVersion getVersion();

    Map<String, String> getHeaders();

    boolean doUpgrade();

    public enum HttpVersion {
        HTTP_1_1,
        EVENT_1_0
    }
}
