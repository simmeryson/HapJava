package com.guok.hap.impl.http;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Map;

/**
 * Created by guokai.
 */
public abstract class AbstractHttpResponse implements HttpResponse {

    public ByteBuffer getBody() {
        return ByteBuffer.allocate(0);
    }

    public HttpVersion getVersion() {
        return HttpVersion.HTTP_1_1;
    }

    public Map<String, String> getHeaders() {
        return Collections.emptyMap();
    }


    public boolean doUpgrade() {
        return false;
    }

}
