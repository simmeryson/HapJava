package com.guok.hap.impl.characteristics.thermostat;

import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitCharacteristicChangeCallback;
import com.guok.hap.accessories.properties.ThermostatMode;
import com.guok.hap.accessories.thermostat.BasicThermostat;
import com.guok.hap.impl.responses.HapStatusCodes;

public class CurrentHeatingCoolingModeCharacteristic extends
		AbstractHeatingCoolingModeCharacteristic {
	
	private final BasicThermostat thermostat;

	public CurrentHeatingCoolingModeCharacteristic(BasicThermostat thermostat) {
		super("0000000F-0000-1000-8000-0026BB765291", false, "Current Mode");
		this.thermostat = thermostat;
	}

	@Override
	protected int setModeValue(ThermostatMode mode) throws Exception {
		//Not writable
		return HapStatusCodes.READ_OLNY;
	}

	@Override
	protected ListenableFuture<ThermostatMode> getModeValue() {
		return thermostat.getCurrentMode();
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		thermostat.subscribeCurrentMode(callback);
	}

	@Override
	public void unsubscribe() {
		thermostat.unsubscribeCurrentMode();
	}

}
