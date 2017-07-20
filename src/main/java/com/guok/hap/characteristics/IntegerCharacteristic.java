package com.guok.hap.characteristics;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import javax.json.JsonNumber;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

/**
 * A characteristic that provides an Integer data type.
 *
 * @author Andy Lintner
 */
public class IntegerCharacteristic extends BaseCharacteristic<Integer> {


    /**
     * Default constructor
     *
     * @param UUID        a string containing a UUID that indicates the type of characteristic. Apple defines a set of these,
     *                    however implementors can create their own as well.
     * @param isWritable  indicates whether the value can be changed.
     * @param isReadable  indicates whether the value can be retrieved.
     * @param description a description of the characteristic to be passed to the consuming device.
     * @param minValue    the minimum supported value.
     * @param maxValue    the maximum supported value
     * @param unit        a description of the unit this characteristic supports.
     */
    public IntegerCharacteristic(String UUID, boolean isWritable, boolean isReadable, String description,
                                 int minValue, int maxValue, CharacteristicUnits unit) {
        super(UUID, CharacteristicValueFormats.INT, isWritable, isReadable, description);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.unit = unit;
        this.minStep = 1;

        this.value = minValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ListenableFuture<JsonObjectBuilder> makeBuilder(int iid) {
        return Futures.transform(super.makeBuilder(iid), new Function<JsonObjectBuilder, JsonObjectBuilder>() {
            @Override
            public JsonObjectBuilder apply(JsonObjectBuilder jsonObjectBuilder) {
                return jsonObjectBuilder
                        .add("minValue", minValue)
                        .add("maxValue", maxValue)
                        .add("minStep", minStep)
                        .add("unit", unit.toString());
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getDefault() {
        return minValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer convert(JsonValue jsonValue) {
        return ((JsonNumber) jsonValue).intValue();
    }

}
