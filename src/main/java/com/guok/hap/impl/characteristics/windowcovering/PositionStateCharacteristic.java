package com.guok.hap.impl.characteristics.windowcovering;

import java.util.concurrent.CompletableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.WindowCovering;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;

public class PositionStateCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

	private final WindowCovering windowCovering;
	
	public PositionStateCharacteristic(WindowCovering windowCovering) {
		super("00000072-0000-1000-8000-0026BB765291", false, true, "The position state", 2);
		this.windowCovering = windowCovering;
	}

	@Override
	protected void setValue(Integer value) throws Exception {
		//Read only
	}

	@Override
	protected CompletableFuture<Integer> getValue() {
		return windowCovering.getPositionState().thenApply(v -> v.getCode());
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		windowCovering.subscribePositionState(callback);
	}

	@Override
	public void unsubscribe() {
		windowCovering.unsubscribePositionState();
	}

}
