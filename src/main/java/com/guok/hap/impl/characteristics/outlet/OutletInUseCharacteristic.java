package com.guok.hap.impl.characteristics.outlet;

import java.util.concurrent.CompletableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.Outlet;
import com.guok.hap.characteristics.BooleanCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;

public class OutletInUseCharacteristic  extends BooleanCharacteristic implements EventableCharacteristic {

	private final Outlet outlet;
	
	public OutletInUseCharacteristic(Outlet outlet) {
		super("00000026-0000-1000-8000-0026BB765291", false, true, "The outlet is in use");
		this.outlet = outlet;
	}

	@Override
	protected void setValue(Boolean value) throws Exception {
		//Read Only
	}

	@Override
	protected CompletableFuture<Boolean> getValue() {
		return outlet.getOutletInUse();
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		outlet.subscribeOutletInUse(callback);
	}

	@Override
	public void unsubscribe() {
		outlet.unsubscribeOutletInUse();
	}

	

}
