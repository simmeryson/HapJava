package com.guok.hap.impl.characteristics.windowcovering;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.WindowCovering;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.IntegerCharacteristic;

public class TargetPositionCharacteristic extends IntegerCharacteristic implements EventableCharacteristic {

	private final WindowCovering windowCovering;
	
	public TargetPositionCharacteristic(WindowCovering windowCovering) {
		super("0000007C-0000-1000-8000-0026BB765291", true, true, "The target position", 0, 100, "%");
		this.windowCovering = windowCovering;
	}

	@Override
	protected void setValue(Integer value) throws Exception {
		windowCovering.setTargetPosition(value);
	}

	@Override
	protected ListenableFuture<Integer> getValue() {
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
