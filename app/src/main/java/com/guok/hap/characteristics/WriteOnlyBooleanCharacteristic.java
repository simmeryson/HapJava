package com.guok.hap.characteristics;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.impl.responses.HapStatusCodes;

import javax.json.JsonObjectBuilder;

/**
 * A characteristic that provides a boolean that can only be written to, not read.
 *
 * @author Andy Lintner
 */
public abstract class WriteOnlyBooleanCharacteristic extends BooleanCharacteristic {
	
	/**
	 * Default constructor
	 * 
	 * @param UUID a string containing a UUID that indicates the type of characteristic. Apple defines a set of these,
	 *  however implementors can create their own as well.
	 * @param description a description of the characteristic to be passed to the consuming device.
	 */
	public WriteOnlyBooleanCharacteristic(String UUID, String description) {
		super(	UUID,
				true,
				false,
				description
			);
	}
	
	@Override
	protected final ListenableFuture<Boolean> getValue() { return Futures.immediateFuture(false); }

	@Override
	protected final int setJsonValue(JsonObjectBuilder builder, Boolean value) {
		//Do nothing - non-readable characteristics cannot have a value key set
		return HapStatusCodes.WRITE_ONLY;
	}
}
