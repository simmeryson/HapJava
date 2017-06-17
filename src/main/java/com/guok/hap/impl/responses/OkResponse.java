package com.guok.hap.impl.responses;

import com.guok.hap.impl.http.AbstractHttpResponse;

import java.nio.ByteBuffer;

public class OkResponse extends AbstractHttpResponse {

	private final ByteBuffer body;
	
	public OkResponse(byte[] body) {
		this.body = ByteBuffer.wrap(body);
	}
	
	@Override
	public ByteBuffer getBody() {
		return body;
	}
	
	@Override
	public int getStatusCode() {
		return 200;
	}

}
