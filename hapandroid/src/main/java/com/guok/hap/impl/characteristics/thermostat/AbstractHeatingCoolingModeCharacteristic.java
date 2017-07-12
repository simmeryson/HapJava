package com.guok.hap.impl.characteristics.thermostat;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.accessories.properties.ThermostatMode;
import com.guok.hap.characteristics.EnumCharacteristic;
import com.guok.hap.characteristics.EventableCharacteristic;


abstract class AbstractHeatingCoolingModeCharacteristic extends EnumCharacteristic implements EventableCharacteristic {

	public AbstractHeatingCoolingModeCharacteristic(String UUID, boolean isWritable, String description) {
		super(UUID, isWritable, true, description, 3);
	}

	@Override
	protected final int setValue(Integer value) throws Exception {
		return setModeValue(ThermostatMode.fromCode(value));
	}

	@Override
	public final ListenableFuture<Integer> getValue() {
		return Futures.transform(getModeValue(), new Function<ThermostatMode, Integer>() {
			@Override
			public Integer apply(ThermostatMode thermostatMode) {
				return thermostatMode.getCode();
			}
		});
	}

	protected abstract int setModeValue(ThermostatMode mode) throws Exception;
	
	protected abstract ListenableFuture<ThermostatMode> getModeValue();
	
	
}