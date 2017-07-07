package com.guok.hap.accessories.properties;

import java.util.HashMap;
import java.util.Map;

public enum CarbonMonoxideDetectedState {

    NORMAL(0),
    ABNORMAL(1);

    private final static Map<Integer, CarbonMonoxideDetectedState> reverse;

    static {
//        reverse = Arrays.stream(CarbonMonoxideDetectedState.values()).collect(Collectors.toMap(CarbonMonoxideDetectedState::getCode, t -> t));
        reverse = new HashMap<>();
        for (CarbonMonoxideDetectedState state : CarbonMonoxideDetectedState.values()) {
            reverse.put(state.getCode(), state);
        }
    }

    public static CarbonMonoxideDetectedState fromCode(Integer code) {
        return reverse.get(code);
    }

    private final int code;

    CarbonMonoxideDetectedState(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
