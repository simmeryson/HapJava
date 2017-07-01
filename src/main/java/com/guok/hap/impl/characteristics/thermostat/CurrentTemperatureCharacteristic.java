package com.guok.hap.impl.characteristics.thermostat;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.TemperatureSensor;
import com.guok.hap.impl.responses.HapStatusCodes;

public class CurrentTemperatureCharacteristic extends
		AbstractTemperatureCharacteristic {

	private final TemperatureSensor sensor;
	
	public CurrentTemperatureCharacteristic(TemperatureSensor thermostat) {
		super("00000011-0000-1000-8000-0026BB765291", false, "Current Temperature", thermostat);
		this.sensor = thermostat;
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		sensor.subscribeCurrentTemperature(callback);
	}

	@Override
	public void unsubscribe() {
		sensor.unsubscribeCurrentTemperature();
	}

	@Override
	protected ListenableFuture<Double> getDoubleValue() {
		return sensor.getCurrentTemperature();
	}

	@Override
	protected int setValue(Double value) throws Exception {
		//Not writable
		return HapStatusCodes.READ_OLNY;
	}

}
