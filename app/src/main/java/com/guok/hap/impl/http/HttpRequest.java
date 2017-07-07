package com.guok.hap.impl.http;

public interface HttpRequest {

	String getUri();
	byte[] getBody();
	HttpMethod getMethod();
	
}
