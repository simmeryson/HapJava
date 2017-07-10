package com.guok.hap.impl.characteristics.windowcovering;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.HorizontalTiltingWindowCovering;
import com.guok.hap.characteristics.CharacteristicUnits;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.IntegerCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;

public class CurrentHorizontalTiltAngleCharacteristic extends IntegerCharacteristic implements EventableCharacteristic {

	private final HorizontalTiltingWindowCovering windowCovering;
	
	public CurrentHorizontalTiltAngleCharacteristic(HorizontalTiltingWindowCovering windowCovering) {
		super("0000006C-0000-1000-8000-0026BB765291", false, true, "The current horizontal tilt angle", -90, 90, CharacteristicUnits.arcdegrees);
		this.windowCovering = windowCovering;
	}

	@Override
	protected int setValue(Integer value) throws Exception {
		//Read Only
		return HapStatusCodes.READ_OLNY;
	}

	@Override
	public ListenableFuture<Integer> getValue() {
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
