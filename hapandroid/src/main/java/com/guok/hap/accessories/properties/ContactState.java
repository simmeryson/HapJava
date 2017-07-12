package com.guok.hap.accessories.properties;

import java.util.HashMap;
import java.util.Map;

public enum ContactState {

    DETECTED(0),
    NOT_DETECTED(1);

    private final static Map<Integer, ContactState> reverse;
    static {
//        reverse = Arrays.stream(ContactState.values()).collect(Collectors.toMap(ContactState::getCode, t -> t));
        reverse = new HashMap<>();
        for (ContactState state : ContactState.values()) {
            reverse.put(state.getCode(), state);
        }
    }

    public static ContactState fromCode(Integer code) {
        return reverse.get(code);
    }

    private final int code;

    ContactState(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
