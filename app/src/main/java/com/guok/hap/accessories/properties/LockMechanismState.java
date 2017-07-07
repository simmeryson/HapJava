package com.guok.hap.accessories.properties;

import com.guok.hap.accessories.LockMechanism;

import java.util.HashMap;
import java.util.Map;

/**
 * The state of a {@link LockMechanism}.
 *
 * @author Andy Lintner
 */
public enum LockMechanismState {
	
	UNSECURED(0),
	SECURED(1),
	JAMMED(2),
	UNKNOWN(3)
	;
	
	private final static Map<Integer, LockMechanismState> reverse;
	static {
//		reverse = Arrays.stream(LockMechanismState.values()).collect(Collectors.toMap(t -> t.getCode(), t -> t));
		reverse = new HashMap<>();
		for (LockMechanismState state : LockMechanismState.values()) {
			reverse.put(state.getCode(), state);
		}
	}
	
	public static LockMechanismState fromCode(Integer code) {
		return reverse.get(code);
	}
	
	private final int code;
	
	private LockMechanismState(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}
