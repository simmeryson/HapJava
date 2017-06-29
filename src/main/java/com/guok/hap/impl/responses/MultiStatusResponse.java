package com.guok.hap.impl.responses;/**
 * Created by guokai on 29/06/2017.
 */

import com.guok.hap.impl.http.AbstractHttpResponse;

/**
 * @author guokai
 */
public class MultiStatusResponse extends AbstractHttpResponse {
    @Override
    public int getStatusCode() {
        return 207;
    }
}
