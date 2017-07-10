package com.guok.hap.impl.characteristics.windowcovering;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.WindowCovering;
import com.guok.hap.characteristics.CharacteristicUnits;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.IntegerCharacteristic;

public class TargetPositionCharacteristic extends IntegerCharacteristic implements EventableCharacteristic {

	private final WindowCovering windowCovering;
	
	public TargetPositionCharacteristic(WindowCovering windowCovering) {
		super("0000007C-0000-1000-8000-0026BB765291", true, true, "The target position", 0, 100, CharacteristicUnits.percentage);
		this.windowCovering = windowCovering;
	}

	@Override
	protected int setValue(Integer value) throws Exception {
		return windowCovering.setTargetPosition(value).get();
	}

	@Override
	public ListenableFuture<Integer> getValue() {
		return windowCovering.getTargetPosition();
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		windowCovering.subscribeTargetPosition(callback);
	}

	@Override
	public void unsubscribe() {
		windowCovering.unsubscribeTargetPosition();
	}

}
