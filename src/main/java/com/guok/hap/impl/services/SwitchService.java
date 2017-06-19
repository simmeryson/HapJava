package com.guok.hap.impl.services;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.Switch;
import com.guok.hap.impl.Consumer;
import com.guok.hap.impl.ExceptionalConsumer;
import com.guok.hap.impl.Supplier;
import com.guok.hap.impl.characteristics.common.PowerStateCharacteristic;

public class SwitchService extends AbstractServiceImpl {

	public SwitchService(Switch switchAccessory) {
		this(switchAccessory, switchAccessory.getLabel());
	}

	public SwitchService(final Switch switchAccessory, String serviceName) {
		super("00000049-0000-1000-8000-0026BB765291", switchAccessory, serviceName);
		addCharacteristic(new PowerStateCharacteristic(
				new Supplier<ListenableFuture<Boolean>>() {
					@Override
					public ListenableFuture<Boolean> get() {
						return switchAccessory.getSwitchState();
					}
				},
				new ExceptionalConsumer<Boolean>() {
					@Override
					public void accept(Boolean v) throws Exception {
						switchAccessory.setSwitchState(v);
					}
				},
				new Consumer<HomekitCharacteristicChangeCallback>() {
					@Override
					public void accept(HomekitCharacteristicChangeCallback c) {
						switchAccessory.subscribeSwitchState(c);
					}
				},
				new Runnable() {
					@Override
					public void run() {
						switchAccessory.unsubscribeSwitchState();
					}
				}
		));
	}

}
