package com.guok.hap.characteristics;

import com.google.common.util.concurrent.ListenableFuture;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

/**
 * Interface for the characteristics provided by a Service.
 *
 * Characteristics are the lowest level building block of the Homekit Accessory Protocol. They define variables
 * that can be retrieved or set by the remote client. Most consumers of this library will be better served by using
 * one of the characteristic classes in {@link com.guok.hap.characteristics} when creating custom accessory types
 * (the standard accessories from {@link com.guok.hap.accessories} already include the necessary characteristics),
 * instead of trying to implement the JSON formats directly.
 *
 * @author Andy Lintner
 */
public interface Characteristic {

	/**
	 * Adds an attribute to the passed JsonObjectBuilder named "value" with the current value of the characteristic.
	 *
	 * @return 0 when set successfully. {@link com.guok.hap.impl.responses.HapStatusCodes} when set failure.
	 * @param characteristicBuilder the JsonObjectBuilder to add the value attribute to.
	 */
	int supplyValue(JsonObjectBuilder characteristicBuilder);

	/**
	 * Creates the JSON representation of the characteristic, in accordance with the Homekit Accessory Protocol.
	 *
	 * @param iid The instance ID of the characteristic to be included in the serialization.
	 * @return the future completing with the resulting JSON.
	 */
	ListenableFuture<JsonObject> toJson(int iid);

	/**
	 * Invoked by the remote client, this updates the current value of the characteristic.
	 *
	 * @return 0 when set successfully. {@link com.guok.hap.impl.responses.HapStatusCodes} when set failure.
	 * @param jsonValue the JSON serialized value to set.
	 */
	int setValue(JsonValue jsonValue);

    /**
     * The type is from UUID that represents each Characteristic.
     *
     * @return
     */
	String getType();

}
