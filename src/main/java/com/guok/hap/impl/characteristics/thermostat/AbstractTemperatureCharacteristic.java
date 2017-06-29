package com.guok.hap.impl.characteristics.thermostat;

import com.guok.hap.accessories.TemperatureSensor;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.characteristics.FloatCharacteristic;

public abstract class AbstractTemperatureCharacteristic extends FloatCharacteristic implements EventableCharacteristic {

	public AbstractTemperatureCharacteristic(String UUID, boolean isWritable, String description, TemperatureSensor sensor) {
		super(UUID, isWritable, true, description, sensor.getMinimumTemperature(), sensor.getMaximumTemperature(),
				0.1, "celsius");
	}

}
