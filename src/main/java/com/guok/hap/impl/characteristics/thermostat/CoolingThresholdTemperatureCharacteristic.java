package com.guok.hap.impl.characteristics.thermostat;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.thermostat.CoolingThermostat;

public class CoolingThresholdTemperatureCharacteristic extends
		AbstractTemperatureCharacteristic {

	private final CoolingThermostat thermostat;
	
	public CoolingThresholdTemperatureCharacteristic(CoolingThermostat thermostat) {
		super("0000000D-0000-1000-8000-0026BB765291", true, "Temperature above which cooling will be active", thermostat);
		this.thermostat = thermostat;
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		thermostat.subscribeCoolingThresholdTemperature(callback);
	}

	@Override
	public void unsubscribe() {
		thermostat.unsubscribeCoolingThresholdTemperature();
	}

	@Override
	protected ListenableFuture<Double> getDoubleValue() {
		return thermostat.getCoolingThresholdTemperature();
	}

	@Override
	protected void setValue(Double value) throws Exception {
		thermostat.setCoolingThresholdTemperature(value);
	}

}
