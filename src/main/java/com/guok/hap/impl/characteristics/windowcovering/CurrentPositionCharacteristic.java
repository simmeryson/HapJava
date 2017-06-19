package com.guok.hap.impl.characteristics.windowcovering;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.WindowCovering;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.IntegerCharacteristic;

public class CurrentPositionCharacteristic extends IntegerCharacteristic implements EventableCharacteristic {

	private final WindowCovering windowCovering;
	
	public CurrentPositionCharacteristic(WindowCovering windowCovering) {
		super("0000006D-0000-1000-8000-0026BB765291", false, true, "The current position", 0, 100, "%");
		this.windowCovering = windowCovering;
	}

	@Override
	protected void setValue(Integer value) throws Exception {
		//Read Only
	}

	@Override
	protected ListenableFuture<Integer> getValue() {
		return windowCovering.getCurrentPosition();
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		windowCovering.subscribeCurrentPosition(callback);
	}

	@Override
	public void unsubscribe() {
		windowCovering.unsubscribeCurrentPosition();
	}

}
