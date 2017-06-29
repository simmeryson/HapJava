package com.guok.hap.impl;

import com.guok.hap.Service;
import com.guok.hap.impl.accessories.Bridge;

import java.util.Collection;
import java.util.Collections;

/**
 * The Bridge Accessory. The instanceID must be 1;
 */
public class HomekitBridge implements Bridge {

	private final String label;
	private final String serialNumber;
	private final String model;
	private final String manufacturer;
	
	public HomekitBridge(String label, String serialNumber, String model,
			String manufacturer) {
		this.label = label;
		this.serialNumber = serialNumber;
		this.model = model;
		this.manufacturer = manufacturer;
	}

	@Override
	public String getLabel() {
		return label;
	}
	
	@Override
	public String getSerialNumber() {
		return serialNumber;
	}
	
	@Override
	public String getModel() {
		return model;
	}
	
	@Override
	public String getManufacturer() {
		return manufacturer;
	}
	
	@Override
	public Collection<Service> getServices() {
		return Collections.emptyList();
	}
	
	@Override
	public int getId() {
		return 1;
	}

	@Override
	public void identify() {
	}
}
