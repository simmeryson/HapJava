package com.haierubic.os.homekitdemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.google.common.escape.Escaper;
import com.google.common.net.UrlEscapers;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.responses.HapStatusCodes;

/**
 * @author guok
 */

public class BroadcastCharactCallback<T> implements CharacteristicCallBack<T> {

    public static final String ACTION = "com.guok.BroadcastCharactCallback.player";
    public static String URI_FORMAT = "homekit://com.guok/homekit?target=%s&object=%s&value=%s";
    public static Escaper queryEscapers = UrlEscapers.urlFormParameterEscaper();
    private Context mContext;

    public BroadcastCharactCallback(Context context) {
        mContext = context.getApplicationContext();
    }

    @Override
    public int setValueCallback(T value) {
        Log.i("GK", "setValueCallback sendBroadcast!" + value);
        String s = String.format(URI_FORMAT,
                queryEscapers.escape("player"),
                queryEscapers.escape("power"),
                queryEscapers.escape(value.toString()));
        Intent intent = new Intent(ACTION, Uri.parse(s));
        mContext.sendBroadcast(intent);
        return HapStatusCodes.SUCCESS;
    }

    @Override
    public int getValueCallback(T value) {
        return 0;
    }
}
