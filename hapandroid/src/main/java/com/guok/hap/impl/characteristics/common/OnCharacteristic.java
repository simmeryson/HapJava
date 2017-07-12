package com.guok.hap.impl.characteristics.common;


import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.BooleanCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.impl.Consumer;
import com.guok.hap.impl.ExceptionalConsumer;
import com.guok.hap.impl.Supplier;
import com.guok.hap.impl.responses.HapStatusCodes;


public class OnCharacteristic extends BooleanCharacteristic implements EventableCharacteristic {

	private final Supplier<ListenableFuture<Boolean>> getter;
	private final ExceptionalConsumer<Boolean> setter;
	private final Consumer<HomekitCharacteristicChangeCallback> subscriber;
	private final Runnable unsubscriber;
	
	public OnCharacteristic(Supplier<ListenableFuture<Boolean>> getter, ExceptionalConsumer<Boolean> setter,
							Consumer<HomekitCharacteristicChangeCallback> subscriber, Runnable unsubscriber) {
		super("00000025-0000-1000-8000-0026BB765291",
				true,
				true,
				"Turn on and off");
		this.getter = getter;
		this.setter = setter;
		this.subscriber = subscriber;
		this.unsubscriber = unsubscriber;
	}

	@Override
	public int setValue(Boolean value) throws Exception {
		setter.accept(value);
		return HapStatusCodes.SUCCESS;
	}

	@Override
	public ListenableFuture<Boolean> getValue() {
		return getter.get();
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		subscriber.accept(callback);
	}
	
	@Override
	public void unsubscribe() {
		unsubscriber.run();
	}
	
}
