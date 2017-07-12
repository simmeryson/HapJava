package com.guok.hap.impl.characteristics.garage;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.GarageDoor;
import com.guok.hap.accessories.properties.DoorState;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;

public class CurrentDoorStateCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

	private final GarageDoor door;
	
	public CurrentDoorStateCharacteristic(GarageDoor door) {
		super("00000032-0000-1000-8000-0026BB765291", true, true, "Target Door State", 1);
		this.door = door;
	}

	@Override
	protected int setValue(Integer value) throws Exception {
		return door.setTargetDoorState(DoorState.fromCode(value)).get();
	}

	@Override
	public ListenableFuture<Integer> getValue() {
		return Futures.transform(door.getTargetDoorState(), new Function<DoorState, Integer>() {
			@Override
			public Integer apply(DoorState doorState) {
				return doorState.getCode();
			}
		});
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		door.subscribeTargetDoorState(callback);
	}

	@Override
	public void unsubscribe() {
		door.unsubscribeTargetDoorState();
	}

}
