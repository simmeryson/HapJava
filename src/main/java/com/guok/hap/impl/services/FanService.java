package com.guok.hap.impl.services;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.Fan;
import com.guok.hap.impl.Consumer;
import com.guok.hap.impl.ExceptionalConsumer;
import com.guok.hap.impl.Supplier;
import com.guok.hap.impl.characteristics.common.PowerStateCharacteristic;
import com.guok.hap.impl.characteristics.fan.RotationDirectionCharacteristic;
import com.guok.hap.impl.characteristics.fan.RotationSpeedCharacteristic;

public class FanService extends AbstractServiceImpl {

	public FanService(Fan fan) {
		this(fan, fan.getLabel());
	}

	public FanService(final Fan fan, String serviceName) {
		super("00000040-0000-1000-8000-0026BB765291", fan, serviceName);
		addCharacteristic(new PowerStateCharacteristic(
				new Supplier<ListenableFuture<Boolean>>() {
					@Override
					public ListenableFuture<Boolean> get() {
						return fan.getFanPower();
					}
				},
				new ExceptionalConsumer<Boolean>() {
					@Override
					public void accept(Boolean v) throws Exception {
						fan.setFanPower(v);
					}
				},
				new Consumer<HomekitCharacteristicChangeCallback>() {
					@Override
					public void accept(HomekitCharacteristicChangeCallback c) {
						fan.subscribeFanPower(c);
					}
				},
				new Runnable() {
					@Override
					public void run() {
						fan.unsubscribeFanPower();
					}
				}
		));
		addCharacteristic(new RotationDirectionCharacteristic(fan));
		addCharacteristic(new RotationSpeedCharacteristic(fan));
	}

}
