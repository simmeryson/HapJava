package com.guok.hap.characteristics;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

/**
 * Characteristic that exposes an Enum value. Enums are represented as an Integer value
 * in the Homekit protocol, and classes extending this one must handle the static mapping
 * to an Integer value.
 *
 * @author Andy Lintner
 */
public abstract class EnumCharacteristic extends BaseCharacteristic<Integer> {

	private final int maxValue;
	
	/**
	 * Default constructor
	 * 
	 * @param UUID a string containing a UUID that indicates the type of characteristic. Apple defines a set of these,
	 *  however implementors can create their own as well.
	 * @param isWritable indicates whether the value can be changed.
	 * @param isReadable indicates whether the value can be retrieved.
	 * @param description a description of the characteristic to be passed to the consuming device.
	 * @param maxValue the number of enum items.
	 */
	public EnumCharacteristic(String UUID, boolean isWritable, boolean isReadable, String description, int maxValue) {
		super(UUID, CharacteristicValueFormats.INT, isWritable, isReadable, description);
		this.maxValue = maxValue;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ListenableFuture<JsonObjectBuilder> makeBuilder(int iid) {
		return Futures.transform(super.makeBuilder(iid), new Function<JsonObjectBuilder, JsonObjectBuilder>() {
			@Override
			public JsonObjectBuilder apply(JsonObjectBuilder builder) {
				return builder
						.add("minValue", 0)
						.add("maxValue", maxValue)
						.add("minStep", 1);
			}
		});
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer convert(JsonValue jsonValue) {
		if (jsonValue instanceof JsonNumber) {
			return ((JsonNumber) jsonValue).intValue();
		} else if (jsonValue == JsonObject.TRUE) {
			return 1; //For at least one enum type (locks), homekit will send a true instead of 1
		} else if (jsonValue == JsonObject.FALSE) {
			return 0;
		} else {
			throw new IndexOutOfBoundsException("Cannot convert "+jsonValue.getClass()+" to int");
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getDefault() {
		return 0;
	}

}
