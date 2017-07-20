package com.guok.hap.impl.characteristics.information;

import com.guok.hap.characteristics.StaticStringCharacteristic;

/**
 * @author guok
 */

public class HardwareRevision extends StaticStringCharacteristic {

    public static final String UUID = "00000053-0000-1000-8000-0026BB765291";
    /**
     * Default constructor
     *
     * @param UUID        a string containing a UUID that indicates the type of characteristic. Apple defines a set of these,
     *                    however implementors can start their own as well.
     * @param description a description of the characteristic to be passed to the consuming device.
     * @param value       the value of the static string.
     */
    public HardwareRevision(String UUID, String description, String value) {
        super(UUID, "Hardware Revision", value);
    }
}
