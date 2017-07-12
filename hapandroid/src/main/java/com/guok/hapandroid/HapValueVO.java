package com.guok.hapandroid;

/**
 * @author guok
 */

public class HapValueVO<T> {
   private String target;
   private String object;
   private String method;
   private T value;
    private boolean subscribe;

    public HapValueVO() {
    }

    public HapValueVO(String target, String object, String method, T value, boolean subscribe) {
        this.target = target;
        this.object = object;
        this.method = method;
        this.value = value;
        this.subscribe = subscribe;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isSubscribe() {
        return subscribe;
    }

    public void setSubscribe(boolean subscribe) {
        this.subscribe = subscribe;
    }

    @Override
    public String toString() {
        return "HapValueVO{" +
                "target='" + target + '\'' +
                ", object='" + object + '\'' +
                ", method='" + method + '\'' +
                ", value=" + value +
                ", subscribe=" + subscribe +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HapValueVO<?> that = (HapValueVO<?>) o;

        if (subscribe != that.subscribe) return false;
        if (target != null ? !target.equals(that.target) : that.target != null) return false;
        if (object != null ? !object.equals(that.object) : that.object != null) return false;
        if (method != null ? !method.equals(that.method) : that.method != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;

    }

    @Override
    public int hashCode() {
        int result = target != null ? target.hashCode() : 0;
        result = 31 * result + (object != null ? object.hashCode() : 0);
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (subscribe ? 1 : 0);
        return result;
    }
}
