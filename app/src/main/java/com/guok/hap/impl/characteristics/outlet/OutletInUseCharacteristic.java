package com.guok.hap.impl.characteristics.outlet;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.Outlet;
import com.guok.hap.characteristics.BooleanCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;

public class OutletInUseCharacteristic  extends BooleanCharacteristic implements EventableCharacteristic {

	private final Outlet outlet;
	
	public OutletInUseCharacteristic(Outlet outlet) {
		super("00000026-0000-1000-8000-0026BB765291", false, true, "The outlet is in use");
		this.outlet = outlet;
	}

	@Override
	protected int setValue(Boolean value) throws Exception {
		return HapStatusCodes.READ_OLNY;
	}

	@Override
	public ListenableFuture<Boolean> getValue() {
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
