package com.guok.hap.characteristics;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.JsonNumber;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

/**
 * A characteristic that provides a Float value type.
 *
 * @author Andy Lintner
 */
public class FloatCharacteristic extends BaseCharacteristic<Double> {

    private final static Logger LOGGER = LoggerFactory.getLogger(FloatCharacteristic.class);

    /**
     * Default constructor
     *
     * @param UUID        a string containing a UUID that indicates the type of characteristic.
     *                    Apple defines a set of these, however implementors can create their own as
     *                    well.
     * @param isWritable  indicates whether the value can be changed.
     * @param isReadable  indicates whether the value can be retrieved.
     * @param description a description of the characteristic to be passed to the consuming device.
     * @param minValue    the minimum supported value.
     * @param maxValue    the maximum supported value
     * @param minStep     the smallest supported step. Values will be rounded to a multiple of
     *                    this.
     * @param unit        a description of the unit this characteristic supports.
     */
    public FloatCharacteristic(String UUID, boolean isWritable, boolean isReadable, String description,
                               double minValue, double maxValue, double minStep, CharacteristicUnits unit) {
        super(UUID, CharacteristicValueFormats.FLOAT, isWritable, isReadable, description);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.minStep = minStep;
        this.unit = unit;

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
    protected Double convert(JsonValue jsonValue) {
        return ((JsonNumber) jsonValue).doubleValue();
    }

    /**
     * {@inheritDoc}. Calls the getDoubleValue method and applies rounding to the minStep supplied
     * in the constructor.
     */
    @Override
    public final ListenableFuture<Double> getValue() {
        final double rounder = 1 / this.minStep;
        ListenableFuture<Double> future = Futures.transform(super.getValue(), new Function<Double, Double>() {
            @Override
            public Double apply(Double d) {

                return d == null ? null : Math.round(d * rounder) / rounder;
            }
        });
        return Futures.transform(future, new Function<Double, Double>() {
            @Override
            public Double apply(Double d) {
                if (d != null) {
                    if (d < minValue) {
                        LOGGER.warn("Detected value out of range " + d
                                + ". Returning min value instead. Characteristic " + this);
                        return minValue;
                    }
                    if (d > maxValue) {
                        LOGGER.warn("Detected value out of range " + d
                                + ". Returning max value instead. Characteristic " + this);
                        return maxValue;
                    }
                    return d;
                }
                return null;
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Double getDefault() {
        return minValue;
    }

    /**
     * Supplies the value of this characteristic as a double.
     *
     * @return a future that will contain the value.
     */
}
