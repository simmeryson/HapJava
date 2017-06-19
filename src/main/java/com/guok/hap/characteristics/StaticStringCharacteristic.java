package com.guok.hap.characteristics;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;

/**
 * A characteristic that provides an immutable String value.
 *
 * @author Andy Lintner
 */
public class StaticStringCharacteristic extends BaseCharacteristic<String> {

	final private static int MAX_LEN = 255;
	
	final private String value;
	
	/**
	 * Default constructor
	 * 
	 * @param type a string containing a UUID that indicates the type of characteristic. Apple defines a set of these,
	 *  however implementors can create their own as well.
	 * @param description a description of the characteristic to be passed to the consuming device.
	 * @param value the value of the static string.
	 */
	public StaticStringCharacteristic(String type, String description, String value) {
		super(type, 
				"string",
				false,
				true, 
				description);
		this.value = value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ListenableFuture<JsonObjectBuilder> makeBuilder(int iid) {
		return Futures.transform(super.makeBuilder(iid), new Function<JsonObjectBuilder, JsonObjectBuilder>() {
			@Override
			public JsonObjectBuilder apply(JsonObjectBuilder builder) {
				return builder.add("maxLen", MAX_LEN);
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String convert(JsonValue jsonValue) {
		return ((JsonString) jsonValue).getString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(String value) throws Exception {
		throw new Exception("Cannot modify static strings");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ListenableFuture<String> getValue() {
		ListenableFuture<String> future = Futures.immediateFuture(value);
		return Futures.transform(future, new Function<String, String>() {
			@Override
			public String apply(String s) {
				return s != null ? s : "Unavailable";
			}
		});
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getDefault() {
		return "Unknown";
	}
	
}
