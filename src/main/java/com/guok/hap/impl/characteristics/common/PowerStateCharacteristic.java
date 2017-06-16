package com.guok.hap.impl.characteristics.common;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.BooleanCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.impl.ExceptionalConsumer;

public class PowerStateCharacteristic extends BooleanCharacteristic implements EventableCharacteristic {

	private final Supplier<CompletableFuture<Boolean>> getter;
	private final ExceptionalConsumer<Boolean> setter;
	private final Consumer<HomekitCharacteristicChangeCallback> subscriber;
	private final Runnable unsubscriber;
	
	public PowerStateCharacteristic(Supplier<CompletableFuture<Boolean>> getter, ExceptionalConsumer<Boolean> setter, 
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
	public void setValue(Boolean value) throws Exception {
		setter.accept(value);
	}

	@Override
	protected CompletableFuture<Boolean> getValue() {
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
