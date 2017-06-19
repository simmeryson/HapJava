package com.guok.hap.impl.characteristics.lightbulb;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.ColorfulLightbulb;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.FloatCharacteristic;

public class HueCharacteristic extends FloatCharacteristic implements EventableCharacteristic {

	private final ColorfulLightbulb lightbulb;
	
	public HueCharacteristic(ColorfulLightbulb lightbulb) {
		super("00000013-0000-1000-8000-0026BB765291", true, true, "Adjust hue of the light", 0, 360, 1, "arcdegrees");
		this.lightbulb = lightbulb;
	}

	@Override
	protected void setValue(Double value) throws Exception {
		lightbulb.setHue(value);
	}

	@Override
	protected ListenableFuture<Double> getDoubleValue() {
		return lightbulb.getHue();
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		lightbulb.subscribeHue(callback);
	}

	@Override
	public void unsubscribe() {
		lightbulb.unsubscribeHue();
	}

	

}
