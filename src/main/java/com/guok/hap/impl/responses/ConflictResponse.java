package com.guok.hap.impl.responses;

import com.guok.hap.impl.http.AbstractHttpResponse;

public class ConflictResponse extends AbstractHttpResponse {

	@Override
	public int getStatusCode() {
		return 409;
	}

}
