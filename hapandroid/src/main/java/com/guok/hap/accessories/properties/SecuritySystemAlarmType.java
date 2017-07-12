package com.guok.hap.accessories.properties;

import com.guok.hap.accessories.SecuritySystem;

import java.util.HashMap;
import java.util.Map;

/**
 * Type of alarm of a {@link SecuritySystem}.
 *
 * @author Gaston Dombiak
 */
public enum SecuritySystemAlarmType {

    /**
     * Alarm conditions are cleared
     */
    CLEARED(0),
    /**
     * Alarm type is not known
     */
    UNKNOWN(1);

    private final static Map<Integer, SecuritySystemAlarmType> reverse;
    static {
//        reverse = Arrays.stream(SecuritySystemAlarmType.values()).collect(Collectors
//                .toMap(SecuritySystemAlarmType::getCode, t -> t));

        reverse = new HashMap<>();
        for (SecuritySystemAlarmType state : SecuritySystemAlarmType.values()) {
            reverse.put(state.getCode(), state);
        }
    }

    public static SecuritySystemAlarmType fromCode(Integer code) {
        return reverse.get(code);
    }

    private final int code;

    SecuritySystemAlarmType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
