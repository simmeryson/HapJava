package com.guok.hap.impl.characteristics.thermostat;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.properties.ThermostatMode;
import com.guok.hap.accessories.thermostat.BasicThermostat;

public class TargetHeatingCoolingModeCharacteristic extends
		AbstractHeatingCoolingModeCharacteristic {

	private final BasicThermostat thermostat;
	
	public TargetHeatingCoolingModeCharacteristic(BasicThermostat thermostat) {
		super("00000033-0000-1000-8000-0026BB765291", true, "Target Mode");
		this.thermostat = thermostat;
	}

	@Override
	protected void setModeValue(ThermostatMode mode) throws Exception {
		thermostat.setTargetMode(mode);
	}

	@Override
	protected ListenableFuture<ThermostatMode> getModeValue() {
		return thermostat.getTargetMode();
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		thermostat.subscribeTargetMode(callback);
	}

	@Override
	public void unsubscribe() {
		thermostat.unsubscribeTargetMode();
	}

}
