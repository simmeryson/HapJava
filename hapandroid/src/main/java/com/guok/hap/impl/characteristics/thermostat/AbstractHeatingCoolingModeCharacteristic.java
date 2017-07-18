package com.guok.hap.impl.characteristics.thermostat;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;


public class AbstractHeatingCoolingModeCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

	public AbstractHeatingCoolingModeCharacteristic(String UUID, boolean isWritable, String description) {
		super(UUID, isWritable, true, description, 3);
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		this.subcribeCallback = callback;
	}

	@Override
	public void unsubscribe() {
		this.subcribeCallback = null;
	}
}
