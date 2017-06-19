package com.guok.hap.impl.characteristics.lightbulb;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.DimmableLightbulb;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.IntegerCharacteristic;

public class BrightnessCharacteristic extends IntegerCharacteristic implements EventableCharacteristic {

	private final DimmableLightbulb lightbulb;
	
	public BrightnessCharacteristic(DimmableLightbulb lightbulb) {
		super("00000008-0000-1000-8000-0026BB765291", true, true, "Adjust brightness of the light", 0,
				100, "%");
		this.lightbulb = lightbulb;
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		lightbulb.subscribeBrightness(callback);
	}

	@Override
	public void unsubscribe() {
		lightbulb.unsubscribeBrightness();
	}

	@Override
	protected void setValue(Integer value) throws Exception {
		lightbulb.setBrightness(value);
	}

	@Override
	protected ListenableFuture<Integer> getValue() {
		return lightbulb.getBrightness();
	}

}
