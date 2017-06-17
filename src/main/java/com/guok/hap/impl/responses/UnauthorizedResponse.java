package com.guok.hap.impl.responses;

import com.guok.hap.impl.http.AbstractHttpResponse;

public class UnauthorizedResponse extends AbstractHttpResponse {

    @Override
    public int getStatusCode() {
        return 401;
    }

}
