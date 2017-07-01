package com.guok.hap.accessories;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.Service;
import com.guok.hap.accessories.properties.DoorState;
import com.guok.hap.impl.services.GarageDoorService;

import java.util.Collection;
import java.util.Collections;

/**
 * A garage door opener, with control and status of a garage door
 *
 * @author Andy Lintner
 */
public abstract class GarageDoor implements HomekitAccessory {

	/**
	 * Retrieves the current state of the door
	 * @return a future which will contain the door's state
	 */
	public abstract ListenableFuture<DoorState> getCurrentDoorState();
	
	/**
	 * Retrieves the targeted state of the door
	 * @return a future which will contain the door's targeted state
	 */
	public abstract ListenableFuture<DoorState> getTargetDoorState();
	
	/**
	 * Retrieves an indicator of an obstruction detected by the door
	 * @return a future which will contain the indicator
	 */
	public abstract ListenableFuture<Boolean> getObstructionDetected();
	
	/**
	 * Sets the targeted state of the door.
	 * @param state the targeted state
	 * @return a future that completes when the change is made
	 * @throws Exception when the change cannot be made
	 */
	public abstract ListenableFuture<Integer> setTargetDoorState(DoorState state) throws Exception;

	/**
	 * Subscribes to changes in the door's state
	 * @param callback the function to call when the state changes
	 */
	public abstract void subscribeCurrentDoorState(HomekitCharacteristicChangeCallback callback);
	
	/**
	 * Subscribes to changes in the door's targeted state
	 * @param callback the function to call when the targeted state changes
	 */
	public abstract void subscribeTargetDoorState(HomekitCharacteristicChangeCallback callback);
	
	/**
	 * Subscribes to changes in the obstruction detected indicator
	 * @param callback the function to call when the indicator chnages
	 */
	public abstract void subscribeObstructionDetected(HomekitCharacteristicChangeCallback callback);

	/**
	 * Unsubscribes from changes in the door's state
	 */
	public abstract void unsubscribeCurrentDoorState();
	
	/**
	 * Unsubscribes from changes in the door's targeted state
	 */
	public abstract void unsubscribeTargetDoorState();
	
	/**
	 * Unsubscribes from changes in the door's obstruction detected indicator
	 */
	public abstract void unsubscribeObstructionDetected();


	@Override
	public Collection<Service> getServices() {
		return Collections.singleton((Service) new GarageDoorService(this));
	}
}
