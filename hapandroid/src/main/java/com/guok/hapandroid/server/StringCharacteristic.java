package com.guok.hapandroid.server;

import com.guok.hap.characteristics.BaseCharacteristic;
import com.guok.hap.characteristics.CharacteristicValueFormats;

import javax.json.JsonString;
import javax.json.JsonValue;

/**
 * @author guok
 */

public class StringCharacteristic extends BaseCharacteristic<String> {

    public StringCharacteristic(String type, boolean isWritable, boolean isReadable, String description, String value) {
        super(type, CharacteristicValueFormats.STRING, isWritable, isReadable, description);

        this.value = value == null ? getDefault() : value;
    }

    @Override
    protected int setValue(String value) throws Exception {
        if (value != null && value.length() > 256) {
            value = value.substring(0, 255);
            logger.warn("String characteristic value too long! cut to 256 length.");
        }
        return super.setValue(value);
    }

    @Override
    protected String convert(JsonValue jsonValue) {
        return ((JsonString) jsonValue).getString();
    }

    @Override
    protected String getDefault() {
        return "Unknown";
    }
}
