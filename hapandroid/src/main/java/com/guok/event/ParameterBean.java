
package com.guok.event;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 传递的参数。
 */
public class ParameterBean implements Parcelable {
    private String key;
    private String value;

    protected ParameterBean(Parcel in) {
        key = in.readString();
        value = in.readString();
    }

    public static final Creator<ParameterBean> CREATOR = new Creator<ParameterBean>() {
        @Override
        public ParameterBean createFromParcel(Parcel in) {
            return new ParameterBean(in);
        }

        @Override
        public ParameterBean[] newArray(int size) {
            return new ParameterBean[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ParameterBean(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public ParameterBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(value);
    }
}
