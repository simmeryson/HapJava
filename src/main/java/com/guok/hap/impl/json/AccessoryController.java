package com.guok.hap.impl.json;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import com.guok.hap.HomekitAccessory;
import com.guok.hap.Service;
import com.guok.hap.characteristics.Characteristic;
import com.guok.hap.impl.HomekitRegistry;
import com.guok.hap.impl.http.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class AccessoryController {

	private final HomekitRegistry registry;
	
	public AccessoryController(HomekitRegistry registry) {
		this.registry = registry;
	}

	/**
	 *	response of controller's request with method "/accessories"
	 * @return json of accessories and their services, characteristics
	 * @throws Exception
	 */
	public HttpResponse listing() throws Exception {
		JsonArrayBuilder accessories = Json.createArrayBuilder();
		
		Map<Integer, List<ListenableFuture<JsonObject>>> accessoryServiceFutures = new HashMap<>();
		for (HomekitAccessory accessory: registry.getAccessories()) {
			int iid = 0;
			List<ListenableFuture<JsonObject>> serviceFutures = new ArrayList<>();
			for (Service service: registry.getServices(accessory.getId())) {
				serviceFutures.add(toJson(service, iid));
				iid += service.getCharacteristics().size() + 1;
			}
			accessoryServiceFutures.put(accessory.getId(), serviceFutures);//turn this accessory's services and characteristics to json
		}
		
		Map<Integer, JsonArrayBuilder> serviceArrayBuilders = new HashMap<>();
		for (Entry<Integer, List<ListenableFuture<JsonObject>>> entry: accessoryServiceFutures.entrySet()) {
			JsonArrayBuilder arr = Json.createArrayBuilder();
			for (ListenableFuture<JsonObject> future: entry.getValue()) {
				arr.add(future.get());
			}
			serviceArrayBuilders.put(entry.getKey(), arr);
		}
		
		for (HomekitAccessory accessory: registry.getAccessories()) {
			accessories.add(Json.createObjectBuilder().add("aid", accessory.getId()).add("services", serviceArrayBuilders.get(accessory.getId())));
		}
						
		try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			JsonObject jsonObject = Json.createObjectBuilder().add("accessories", accessories).build();
			Json.createWriter(baos).write(
					jsonObject
			);
			return new HapJsonResponse(baos.toByteArray());
		}
	}
	
	private ListenableFuture<JsonObject> toJson(Service service, int instanceID) throws Exception {
		final JsonObjectBuilder builder = Json.createObjectBuilder()
			.add("iid", ++instanceID)
			.add("type", service.getType());
		Collection<Characteristic> characteristics = service.getCharacteristics();
		Collection<ListenableFuture<JsonObject>> characteristicFutures = new ArrayList<>(characteristics.size());
		for (Characteristic characteristic: characteristics) {
			characteristicFutures.add(characteristic.toJson(++instanceID));
		}
		ListenableFuture<List<JsonObject>> successfulAsList =
                Futures.successfulAsList(characteristicFutures.toArray(new ListenableFuture[characteristicFutures.size()]));
		return Futures.transform(successfulAsList, new Function<List<JsonObject>, JsonObject>() {
			@Override
			public JsonObject apply(List<JsonObject> jsonObjects) {
				JsonArrayBuilder jsonCharacteristics = Json.createArrayBuilder();
				for (JsonObject jsonObject : jsonObjects) {
					jsonCharacteristics.add(jsonObject);
				}
				builder.add("characteristics", jsonCharacteristics);
				return builder.build();
			}
		});
	}
}
