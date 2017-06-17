package com.guok.hap.accessories.properties;

import java.util.HashMap;
import java.util.Map;

public enum SmokeDetectedState {

    NOT_DETECTED(0),
    DETECTED(1);

    private final static Map<Integer, SmokeDetectedState> reverse;
    static {
//        reverse = Arrays.stream(SmokeDetectedState.values()).collect(Collectors.toMap(SmokeDetectedState::getCode, t -> t));
        reverse = new HashMap<>();
        for (SmokeDetectedState state : SmokeDetectedState.values()) {
            reverse.put(state.getCode(), state);
        }
    }

    public static SmokeDetectedState fromCode(Integer code) {
        return reverse.get(code);
    }

    private final int code;

    SmokeDetectedState(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
