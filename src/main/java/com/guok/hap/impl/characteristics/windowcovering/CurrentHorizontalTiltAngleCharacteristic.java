package com.guok.hap.impl.characteristics.windowcovering;

import java.util.concurrent.CompletableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.HorizontalTiltingWindowCovering;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.IntegerCharacteristic;

public class CurrentHorizontalTiltAngleCharacteristic extends IntegerCharacteristic implements EventableCharacteristic {

	private final HorizontalTiltingWindowCovering windowCovering;
	
	public CurrentHorizontalTiltAngleCharacteristic(HorizontalTiltingWindowCovering windowCovering) {
		super("0000006C-0000-1000-8000-0026BB765291", false, true, "The current horizontal tilt angle", -90, 90, "Arc Degree");
		this.windowCovering = windowCovering;
	}

	@Override
	protected void setValue(Integer value) throws Exception {
		//Read Only
	}

	@Override
	protected CompletableFuture<Integer> getValue() {
		return windowCovering.getCurrentHorizontalTiltAngle();
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		windowCovering.subscribeCurrentHorizontalTiltAngle(callback);
	}

	@Override
	public void unsubscribe() {
		windowCovering.unsubscribeCurrentHorizontalTiltAngle();
	}

}