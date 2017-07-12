package com.guok.hap.impl.characteristics.garage;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.GarageDoor;
import com.guok.hap.accessories.properties.DoorState;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.impl.responses.HapStatusCodes;

public class TargetDoorStateCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

	private final GarageDoor door;
	
	public TargetDoorStateCharacteristic(GarageDoor door) {
		super("0000000E-0000-1000-8000-0026BB765291", false, true, "Current Door State", 4);
		this.door = door;
	}

	@Override
	protected int setValue(Integer value) throws Exception {
		//Read Only
		return HapStatusCodes.READ_OLNY;
	}

	@Override
	public ListenableFuture<Integer> getValue() {
		return Futures.transform(door.getCurrentDoorState(), new Function<DoorState, Integer>() {
			@Override
			public Integer apply(DoorState doorState) {
				return doorState.getCode();
			}
		});
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		door.subscribeCurrentDoorState(callback);
	}

	@Override
	public void unsubscribe() {
		door.unsubscribeCurrentDoorState();
	}

}
