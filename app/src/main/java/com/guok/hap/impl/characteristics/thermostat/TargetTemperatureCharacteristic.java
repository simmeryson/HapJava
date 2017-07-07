package com.guok.hap.impl.characteristics.thermostat;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.thermostat.BasicThermostat;

public class TargetTemperatureCharacteristic extends
		AbstractTemperatureCharacteristic {
	
	private final BasicThermostat thermostat;

	public TargetTemperatureCharacteristic(BasicThermostat thermostat) {
		super("00000035-0000-1000-8000-0026BB765291", true, "Target Temperature", thermostat);
		this.thermostat = thermostat;
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		thermostat.subscribeTargetTemperature(callback);
	}

	@Override
	public void unsubscribe() {
		thermostat.unsubscribeTargetTemperature();
	}

	@Override
	protected ListenableFuture<Double> getDoubleValue() {
		return thermostat.getTargetTemperature();
	}

	@Override
	protected int setValue(Double value) throws Exception {
		return thermostat.setTargetTemperature(value);
	}

}
