package com.guok.hap.impl.characteristics.lightbulb;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.ColorfulLightbulb;
import com.guok.hap.characteristics.CharacteristicUnits;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.FloatCharacteristic;

public class SaturationCharacteristic extends FloatCharacteristic implements EventableCharacteristic {

	private final ColorfulLightbulb lightbulb;
	
	public SaturationCharacteristic(ColorfulLightbulb lightbulb) {
		super("0000002F-0000-1000-8000-0026BB765291", true, true, "Adjust saturation of the light", 0,
				100, 1, CharacteristicUnits.percentage);
		this.lightbulb = lightbulb;
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		lightbulb.subscribeSaturation(callback);
	}

	@Override
	public void unsubscribe() {
		lightbulb.unsubscribeSaturation();
	}

	@Override
	protected int setValue(Double value) throws Exception {
		return lightbulb.setSaturation(value).get();
	}

	@Override
	protected ListenableFuture<Double> getDoubleValue() {
		return lightbulb.getSaturation();
	}

}