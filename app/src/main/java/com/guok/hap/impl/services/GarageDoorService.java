package com.guok.hap.impl.services;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.GarageDoor;
import com.guok.hap.impl.Consumer;
import com.guok.hap.impl.Supplier;
import com.guok.hap.impl.characteristics.common.ObstructionDetectedCharacteristic;
import com.guok.hap.impl.characteristics.garage.CurrentDoorStateCharacteristic;
import com.guok.hap.impl.characteristics.garage.TargetDoorStateCharacteristic;

public class GarageDoorService extends AbstractServiceImpl {

	public GarageDoorService(GarageDoor door) {
		this(door, door.getLabel());
	}

	public GarageDoorService(final GarageDoor door, String serviceName) {
		super("00000041-0000-1000-8000-0026BB765291", door, serviceName);
		addCharacteristic(new CurrentDoorStateCharacteristic(door));
		addCharacteristic(new TargetDoorStateCharacteristic(door));
		addCharacteristic(new ObstructionDetectedCharacteristic(
				new Supplier<ListenableFuture<Boolean>>() {
					@Override
					public ListenableFuture<Boolean> get() {
						return door.getObstructionDetected();
					}
				},
				new Consumer<HomekitCharacteristicChangeCallback>() {
					@Override
					public void accept(HomekitCharacteristicChangeCallback c) {
						door.subscribeObstructionDetected(c);
					}
				},
				new Runnable() {
					@Override
					public void run() {
						door.unsubscribeObstructionDetected();
					}
				}
		));
	}

}
