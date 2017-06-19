package com.guok.hap.impl.characteristics.windowcovering;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.VerticalTiltingWindowCovering;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.IntegerCharacteristic;

public class TargetVerticalTiltAngleCharacteristic extends IntegerCharacteristic implements EventableCharacteristic {

	private final VerticalTiltingWindowCovering windowCovering;
	
	public TargetVerticalTiltAngleCharacteristic(VerticalTiltingWindowCovering windowCovering) {
		super("0000007D-0000-1000-8000-0026BB765291", true, true, "The target vertical tilt angle", -90, 90, "Arc Degree");
		this.windowCovering = windowCovering;
	}

	@Override
	protected void setValue(Integer value) throws Exception {
		windowCovering.setTargetVerticalTiltAngle(value);
	}

	@Override
	protected ListenableFuture<Integer> getValue() {
		return windowCovering.getTargetVerticalTiltAngle();
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		windowCovering.subscribeTargetVerticalTiltAngle(callback);
	}

	@Override
	public void unsubscribe() {
		windowCovering.unsubscribeTargetVerticalTiltAngle();
	}

}
