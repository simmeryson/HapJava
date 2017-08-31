package com.guok.hapandroid;

/**
 * @author guok
 */

public class HapValueVO<T> {
   private String domain;
   private String target;
   private String method;
   private T value;
    private boolean subscribe;

    public HapValueVO() {
    }

    public HapValueVO(String domain, String target, String method, T value, boolean subscribe) {
        this.domain = domain;
        this.target = target;
        this.method = method;
        this.value = value;
        this.subscribe = subscribe;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
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
                "domain='" + domain + '\'' +
                ", target='" + target + '\'' +
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
        if (domain != null ? !domain.equals(that.domain) : that.domain != null) return false;
        if (target != null ? !target.equals(that.target) : that.target != null) return false;
        if (method != null ? !method.equals(that.method) : that.method != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;

    }

    @Override
    public int hashCode() {
        int result = domain != null ? domain.hashCode() : 0;
        result = 31 * result + (target != null ? target.hashCode() : 0);
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (subscribe ? 1 : 0);
        return result;
    }
}
