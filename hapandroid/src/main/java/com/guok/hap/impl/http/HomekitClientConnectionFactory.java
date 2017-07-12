package com.guok.hap.impl.http;

import com.guok.hap.impl.Consumer;

public interface HomekitClientConnectionFactory {

	HomekitClientConnection createConnection(Consumer<HttpResponse> outOfBandMessageCallback);
	
}
