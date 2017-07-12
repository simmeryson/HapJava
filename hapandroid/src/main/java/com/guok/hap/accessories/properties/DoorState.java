package com.guok.hap.accessories.properties;

import java.util.HashMap;
import java.util.Map;

public enum DoorState {

	OPEN(0),
	CLOSED(1),
	OPENING(2),
	CLOSING(3),
	STOPPED(4);
	

	private final static Map<Integer, DoorState> reverse;
	static {
//		reverse = Arrays.stream(DoorState.values()).collect(Collectors.toMap(t -> t.getCode(), t -> t));
		reverse = new HashMap<>();
		for (DoorState state : DoorState.values()) {
			reverse.put(state.getCode(), state);
		}
	}
	
	public static DoorState fromCode(Integer code) {
		return reverse.get(code);
	}
	
	private final int code;
	
	private DoorState(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}
