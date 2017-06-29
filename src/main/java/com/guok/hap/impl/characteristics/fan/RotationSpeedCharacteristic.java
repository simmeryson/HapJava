package com.guok.hap.impl.characteristics.fan;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.Fan;
import com.guok.hap.characteristics.CharacteristicUnits;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.IntegerCharacteristic;

public class RotationSpeedCharacteristic extends IntegerCharacteristic implements EventableCharacteristic {

	private final Fan fan;
	
	public RotationSpeedCharacteristic(Fan fan) {
		super("00000029-0000-1000-8000-0026BB765291", true, true, "Rotation speed", 0,
				100, CharacteristicUnits.percentage);
		this.fan = fan;
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		fan.subscribeRotationSpeed(callback);
	}

	@Override
	public void unsubscribe() {
		fan.unsubscribeRotationSpeed();
	}

	@Override
	protected void setValue(Integer value) throws Exception {
		fan.setRotationSpeed(value);
	}

	@Override
	protected ListenableFuture<Integer> getValue() {
		return fan.getRotationSpeed();
	}

}
