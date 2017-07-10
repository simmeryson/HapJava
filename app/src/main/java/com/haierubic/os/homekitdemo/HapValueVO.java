package com.haierubic.os.homekitdemo;

/**
 * @author guok
 */

public class HapValueVO<T> {
   private String targe;
   private String object;
   private String method;
   private T value;

    public HapValueVO() {
    }

    public HapValueVO(String targe, String object, String method, T value) {
        this.targe = targe;
        this.object = object;
        this.method = method;
        this.value = value;
    }

    public String getTarge() {
        return targe;
    }

    public void setTarge(String targe) {
        this.targe = targe;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HapValueVO<?> that = (HapValueVO<?>) o;

        if (targe != null ? !targe.equals(that.targe) : that.targe != null) return false;
        if (object != null ? !object.equals(that.object) : that.object != null) return false;
        if (method != null ? !method.equals(that.method) : that.method != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;

    }

    @Override
    public int hashCode() {
        int result = targe != null ? targe.hashCode() : 0;
        result = 31 * result + (object != null ? object.hashCode() : 0);
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HapValueVO{" +
                "targe='" + targe + '\'' +
                ", object='" + object + '\'' +
                ", method='" + method + '\'' +
                ", value=" + value +
                '}';
    }
}
