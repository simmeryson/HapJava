package com.guok.hap.impl.services;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.Outlet;
import com.guok.hap.impl.Consumer;
import com.guok.hap.impl.ExceptionalConsumer;
import com.guok.hap.impl.Supplier;
import com.guok.hap.impl.characteristics.common.OnCharacteristic;
import com.guok.hap.impl.characteristics.outlet.OutletInUseCharacteristic;

public class OutletService extends AbstractServiceImpl {

	public OutletService(Outlet outlet) {
		this(outlet, outlet.getLabel());
	}

	public OutletService(final Outlet outlet, String serviceName) {
		super("00000047-0000-1000-8000-0026BB765291", outlet, serviceName);
		addCharacteristic(new OnCharacteristic(
				new Supplier<ListenableFuture<Boolean>>() {
					@Override
					public ListenableFuture<Boolean> get() {
						return outlet.getPowerState();
					}
				},
				new ExceptionalConsumer<Boolean>() {
					@Override
					public void accept(Boolean v) throws Exception {
						outlet.setPowerState(v);
					}
				},
				new Consumer<HomekitCharacteristicChangeCallback>() {
					@Override
					public void accept(HomekitCharacteristicChangeCallback c) {
						outlet.subscribePowerState(c);
					}
				},
				new Runnable() {
					@Override
					public void run() {
						outlet.unsubscribePowerState();
					}
				}
		));
		addCharacteristic(new OutletInUseCharacteristic(outlet));
	}

}
