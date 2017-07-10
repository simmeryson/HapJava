package com.haierubic.os.homekitdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.util.Log;

import com.google.common.escape.Escaper;
import com.google.common.net.UrlEscapers;
import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.guok.hap.characteristics.BaseCharacteristic;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.responses.HapStatusCodes;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.netty.handler.codec.http.QueryStringDecoder;

/**
 * @author guok
 */

public class BroadcastCharactCallback<T> implements CharacteristicCallBack<T> {

    public static final String SEND_ACTION = "com.guok.BroadcastCharactCallback.send";
    public static final String RECEIVE_ACTION = "com.guok.BroadcastCharactCallback.receive";

    public static final String HAP_SCHEME = "homekit";
    public static String URI_FORMAT = HAP_SCHEME + "://com.guok/homekit?target=%s&object=%s&method=%s&value=%s";
    public static Escaper queryEscapers = UrlEscapers.urlFormParameterEscaper();
    private static Context mContext;
    private HapReceiver sHapReceiver;
    private volatile CountDownLatch sLatch;

    private final String target;
    private final String object;

    private FetchCallBack<T> mFetchCallBack;

    public BroadcastCharactCallback(String target, String object) {
        this.target = target;
        this.object = object;

        sHapReceiver = new HapReceiver();
        IntentFilter filter = new IntentFilter(BroadcastCharactCallback.RECEIVE_ACTION);
        mContext.registerReceiver(sHapReceiver, filter);
    }

    public static void setContext(Context mContext) {
        BroadcastCharactCallback.mContext = mContext.getApplicationContext();
    }

    @Override
    public int setValueCallback(T value) {
        Log.i("GK", "setValueCallback sendBroadcast!" + value);
        String s = String.format(URI_FORMAT,
                queryEscapers.escape(this.target),
                queryEscapers.escape(this.object),
                queryEscapers.escape("set"),
                queryEscapers.escape(value.toString()));
        Intent intent = new Intent(SEND_ACTION, Uri.parse(s));
        mContext.sendBroadcast(intent);
        return HapStatusCodes.SUCCESS;
    }

    @Override
    public ListenableFuture<T> getValueCallback(BaseCharacteristic<T> characteristic, FetchCallBack<T> callBack) {
        Log.i("GK", "getValueCallback sendBroadcast!");
        mFetchCallBack = callBack;
        sLatch = new CountDownLatch(1);
        String s = String.format(URI_FORMAT,
                queryEscapers.escape(this.target),
                queryEscapers.escape(this.object),
                queryEscapers.escape("get"),
                queryEscapers.escape(""));
        Intent intent = new Intent(SEND_ACTION, Uri.parse(s));
        mContext.sendBroadcast(intent);
        try {
            sLatch.await(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return characteristic.getValueImmediately();
    }


    class HapReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && BroadcastCharactCallback.RECEIVE_ACTION.equals(intent.getAction())) {
                String value1 = intent.getStringExtra("value");
                if (value1 == null) return;
                HapValueVO<T> vo = new Gson().fromJson(value1, new TypeToken<HapValueVO<T>>() {
                }.getType());
                System.out.println("HapReceiver get response: " + vo.toString());
                mFetchCallBack.fetchValue(vo.getValue());
                sLatch.countDown();
//            getValueFromUri(intent);
            }
        }
    }

    private void getValueFromUri(Intent intent) {
        Uri data = intent.getData();
        if (data != null && data.getScheme().equals(BroadcastCharactCallback.HAP_SCHEME)) {
            QueryStringDecoder decoder = new QueryStringDecoder(data.toString());
            List<String> target = decoder.parameters().get("target");
            if (target == null || target.size() == 0 || !target.get(0).equals(target)) return;
            List<String> object = decoder.parameters().get("object");
            if (object == null || object.size() == 0 || !object.get(0).equals(object)) return;
            List<String> method = decoder.parameters().get("method");
            if (method == null || method.size() == 0 || !method.get(0).equals("get")) return;
            List<String> value = decoder.parameters().get("value");
            if (value != null && value.size() > 0) {
                mFetchCallBack.fetchValue((T) value.get(0));
            }
        }
    }
}
