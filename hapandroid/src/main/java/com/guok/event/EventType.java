package com.guok.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * @author guok
 */

public class EventType {

    public enum Method {SET, GET, SUB}

    private String domain;
    private String target;
    private List<ParameterBean> values;

    private final AtomicBoolean isDoned = new AtomicBoolean(false);
    private String resultDone;
    private ActionCallback mCallback;
    private GetMethodCallBack mGetMethodCallBack;
    private SubscribeMethodInfo mSubscribeMethodInfo;
    private Method mMethod;

    public EventType(EventType eventType) {
        this.domain = eventType.getDomain();
        this.target = eventType.getTarget();
        this.values = eventType.getValues();
    }

    public EventType(String domain, String target) {
        this.domain = domain;
        this.target = target;
    }

    public EventType(String domain, String target, List<ParameterBean> values) {
        this.domain = domain;
        this.target = target;
        this.values = values;
    }

    public EventType(String domain, String target, List<ParameterBean> values, Method method) {
        this.domain = domain;
        this.target = target;
        this.values = values;
        mMethod = method;
    }

    public static EventType fromEvent(Event event) {
        if (event != null) {
            String target = event.getTarget();
            String domain = event.getDomain();
            List<ParameterBean> values = event.getValues();

            EventType type = new EventType(domain, target);
            type.values = values;
            return type;
        }
        return null;
    }

    public static Map<String, String> parseValues(EventType eventType) {
        List<ParameterBean> values = eventType.getValues();
        Map<String, String> stringMap = new HashMap<>();
        if (values != null) {
            for (ParameterBean bean : values) {
                String key = bean.getKey();
                String value = bean.getValue();
                if (isEmpty(key) || isEmpty(value))
                    continue;
                stringMap.put(key.trim().toLowerCase(), value.trim().toLowerCase());
            }
        }
        return stringMap;
    }

    public static String getValue(EventType eventType, String key) {
        if (eventType == null || isEmpty(key))
            return "";
        String val = parseValues(eventType).get(key);
        return isEmpty(val) ? "" : val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        EventType eventType = (EventType) o;

        return target.equals(eventType.target);

    }

    @Override
    public int hashCode() {
        return target.hashCode();
    }


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setValues(List<ParameterBean> values) {
        this.values = values;
    }

    public String getTarget() {
        return target;
    }

    public List<ParameterBean> getValues() {
        return values;
    }

    public boolean isDoned() {
        return isDoned.get();
    }

    public synchronized void setDone(boolean done) {
//        isDoned.getAndSet(done);
        isDoned.set(done);
    }

    public String getResultDone() {
        return resultDone;
    }

    public void setResultDone(String resultDone) {
        this.resultDone = resultDone;
    }

    public ActionCallback getCallback() {
        return mCallback;
    }

    public void setCallback(ActionCallback callback) {
        mCallback = callback;
    }

    public GetMethodCallBack getGetMethodCallBack() {
        return mGetMethodCallBack;
    }

    public void setGetMethodCallBack(GetMethodCallBack getMethodCallBack) {
        mGetMethodCallBack = getMethodCallBack;
    }

    public SubscribeMethodInfo getSubscribeMethodInfo() {
        return mSubscribeMethodInfo;
    }

    public void setSubscribeMethodInfo(SubscribeMethodInfo subscribeMethodInfo) {
        mSubscribeMethodInfo = subscribeMethodInfo;
    }

    public Method getMethod() {
        return mMethod;
    }

    public EventType setMethod(Method method) {
        mMethod = method;
        return this;
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }
}
