package com.guok.hap.accessories.properties;

import com.guok.hap.accessories.TemperatureSensor;

import java.util.HashMap;
import java.util.Map;

/**
 * The temperature unit used by a {@link TemperatureSensor}.
 *
 * @author Andy Lintner
 */
public enum TemperatureUnit {

	CELSIUS(0),
	FAHRENHEIT(1)
	;
	
	private final static Map<Integer, TemperatureUnit> reverse;
	static {
//		reverse = Arrays.stream(TemperatureUnit.values()).collect(Collectors.toMap(t -> t.getCode(), t -> t));
		reverse = new HashMap<>();
		for (TemperatureUnit state : TemperatureUnit.values()) {
			reverse.put(state.getCode(), state);
		}
	}
	
	static TemperatureUnit fromCode(Integer code) {
		return reverse.get(code);
	}
	
	private final int code;
	
	private TemperatureUnit(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}
