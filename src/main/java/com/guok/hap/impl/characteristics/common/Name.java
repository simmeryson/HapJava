package com.guok.hap.impl.characteristics.common;

import com.guok.hap.characteristics.StaticStringCharacteristic;

public class Name extends StaticStringCharacteristic {

	public static final String UUID = "00000023-0000-1000-8000-0026BB765291";

	public Name(String label) {
		super(UUID,
				"Name of the accessory",
				label);
	}
}
