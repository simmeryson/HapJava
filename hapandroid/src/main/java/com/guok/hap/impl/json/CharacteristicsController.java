package com.guok.hap.impl.json;

import com.guok.hap.characteristics.Characteristic;
import com.guok.hap.characteristics.EventableCharacteristic;
import com.guok.hap.impl.HomekitRegistry;
import com.guok.hap.impl.connections.SubscriptionManager;
import com.guok.hap.impl.http.HomekitClientConnection;
import com.guok.hap.impl.http.HttpRequest;
import com.guok.hap.impl.http.HttpResponse;
import com.guok.hap.impl.responses.GeneralErrorResponse;
import com.guok.hap.impl.responses.HapStatusCodes;
import com.guok.hap.impl.responses.HttpStatusCodes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

/**
 * only receiving "ev" param in request , the characteristic begin to push "change" value.
 */
public class CharacteristicsController {

    Logger logger = LoggerFactory.getLogger(CharacteristicsController.class);

    private final HomekitRegistry registry;
    private final SubscriptionManager subscriptions;

    public CharacteristicsController(HomekitRegistry registry, SubscriptionManager subscriptions) {
        this.registry = registry;
        this.subscriptions = subscriptions;
    }

    public HttpResponse get(HttpRequest request) throws Exception {
        String uri = request.getUri();
        // Characteristics are requested with /characteristics?id=1.1,2.1,3.1
        String query = uri.substring("/characteristics?id=".length());
        String[] ids = query.split(",");
        JsonArrayBuilder characteristics = Json.createArrayBuilder();
        for (String id : ids) {
            String[] parts = id.split("\\.");
            if (parts.length != 2) {
                logger.error("Unexpected characteristics request: " + uri);
                return new GeneralErrorResponse(HttpStatusCodes.NOT_FOUND);
            }
            int aid = Integer.parseInt(parts[0]);
            int iid = Integer.parseInt(parts[1]);
            JsonObjectBuilder characteristic = Json.createObjectBuilder();
            Map<Integer, Characteristic> characteristicMap = registry.getCharacteristics(aid);
            if (!characteristicMap.isEmpty()) {
                Characteristic targetCharacteristic = characteristicMap.get(iid);
                if (targetCharacteristic != null) {
                    targetCharacteristic.supplyValue(characteristic);

                    characteristics.add(characteristic.add("aid", aid).add("iid", iid).build());
                } else {
                    logger.warn("Accessory " + aid + " does not have characteristic " + iid + "Request: " + uri);
                }
            } else {
                logger.warn("Accessory " + aid + " has no characteristics or does not exist. Request: " + uri);
            }
        }
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Json.createWriter(baos).write(Json.createObjectBuilder().add("characteristics", characteristics.build()).build());
            return new HapJsonResponse(baos.toByteArray());
        }
    }

    public HttpResponse put(HttpRequest request, HomekitClientConnection connection) throws Exception {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(request.getBody())) {
            JsonArray jsonCharacteristics = Json.createReader(bais)
                    .readObject().getJsonArray("characteristics");

            if (jsonCharacteristics.size() == 0) {
                return new GeneralErrorResponse(HttpStatusCodes.BAD_REQUEST, (JsonObject) null);
            }

            JsonArrayBuilder resultsBuilder = Json.createArrayBuilder();

            boolean hasFail = false;
            for (JsonValue value : jsonCharacteristics) {
                JsonObject jsonCharacteristic = (JsonObject) value;
                int aid = jsonCharacteristic.getInt("aid");
                int iid = jsonCharacteristic.getInt("iid");
                Characteristic characteristic = registry.getCharacteristics(aid).get(iid);

                if (characteristic == null) {
                    return new GeneralErrorResponse(HttpStatusCodes.UNPROCESSABLE, HapStatusCodes.INVALID_VALUE);
                }

                JsonObjectBuilder resBuilder = Json.createObjectBuilder();
                if (jsonCharacteristic.containsKey("value")) {
                    int res = characteristic.setValue(jsonCharacteristic.get("value"));
                    if (res != 0) hasFail = true;
                    resBuilder.add("aid", aid).add("iid", iid).add("status", res);
                    resultsBuilder.add(resBuilder);
                }

                //only receiving "ev" param in request , the characteristic begin to push "change" value.
                if (jsonCharacteristic.containsKey("ev")) {
                    if (characteristic instanceof EventableCharacteristic) {
                        if (jsonCharacteristic.getBoolean("ev")) {
                            subscriptions.addSubscription(aid, iid, (EventableCharacteristic) characteristic, connection);
                        } else {
                            subscriptions.removeSubscription((EventableCharacteristic) characteristic, connection);
                        }
                    } else {
                        return new GeneralErrorResponse(HttpStatusCodes.MULTI_STATUS, HapStatusCodes.NOTIFICATION_UNSUPPORT);
                    }
                }

                if (hasFail){
                    JsonObject jsonObject = Json.createObjectBuilder().add("accessories", resultsBuilder).build();
                    if (jsonCharacteristics.size() > 1) {
                        return new GeneralErrorResponse(HttpStatusCodes.MULTI_STATUS, jsonObject);
                    } else if (jsonCharacteristics.size() == 1) {
                        return new GeneralErrorResponse(HttpStatusCodes.BAD_REQUEST, jsonObject);
                    }
                }
            }
        }
        return new HapJsonNoContentResponse();
    }

}
