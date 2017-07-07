package com.guok.hap.impl.services;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.ColorfulLightbulb;
import com.guok.hap.accessories.DimmableLightbulb;
import com.guok.hap.accessories.Lightbulb;
import com.guok.hap.impl.Consumer;
import com.guok.hap.impl.ExceptionalConsumer;
import com.guok.hap.impl.Supplier;
import com.guok.hap.impl.characteristics.common.OnCharacteristic;
import com.guok.hap.impl.characteristics.lightbulb.BrightnessCharacteristic;
import com.guok.hap.impl.characteristics.lightbulb.HueCharacteristic;
import com.guok.hap.impl.characteristics.lightbulb.SaturationCharacteristic;

public class LightbulbService extends AbstractServiceImpl {

	public LightbulbService(Lightbulb lightbulb) {
		this(lightbulb, lightbulb.getLabel());
	}

	public LightbulbService(final Lightbulb lightbulb, String serviceName) {
		super("00000043-0000-1000-8000-0026BB765291", lightbulb, serviceName);
		addCharacteristic(new OnCharacteristic(
				new Supplier<ListenableFuture<Boolean>>() {
					@Override
					public ListenableFuture<Boolean> get() {
						return lightbulb.getLightbulbPowerState();
					}
				},
				new ExceptionalConsumer<Boolean>() {
					@Override
					public void accept(Boolean v) throws Exception {
						lightbulb.setLightbulbPowerState(v);
					}
				},
				new Consumer<HomekitCharacteristicChangeCallback>() {
					@Override
					public void accept(HomekitCharacteristicChangeCallback c) {
						lightbulb.subscribeLightbulbPowerState(c);
					}
				},
				new Runnable() {
					@Override
					public void run() {
						lightbulb.unsubscribeLightbulbPowerState();
					}
				}
		));
		
		if (lightbulb instanceof DimmableLightbulb) {
			addCharacteristic(new BrightnessCharacteristic((DimmableLightbulb) lightbulb));
		}
		
		if (lightbulb instanceof ColorfulLightbulb) {
			addCharacteristic(new HueCharacteristic((ColorfulLightbulb) lightbulb));
			addCharacteristic(new SaturationCharacteristic((ColorfulLightbulb) lightbulb));
		}
	}

}
