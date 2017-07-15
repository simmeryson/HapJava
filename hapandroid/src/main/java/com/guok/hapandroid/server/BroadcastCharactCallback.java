package com.guok.hapandroid.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.google.common.escape.Escaper;
import com.google.common.net.UrlEscapers;
import com.google.common.util.concurrent.ListenableFuture;
import com.guok.hap.characteristics.BaseCharacteristic;
import com.guok.hap.characteristics.CharacteristicCallBack;
import com.guok.hap.impl.responses.HapStatusCodes;
import com.guok.hapandroid.HapValueVO;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.guok.hap.impl.HomekitUtils.getSingleGenericType;

/**
 * @author guok
 */

public class BroadcastCharactCallback<T> implements CharacteristicCallBack<T> {

    public static final String SEND_ACTION = "com.guok.BroadcastCharactCallback.send";
    public static final String RECEIVE_ACTION = "com.guok.BroadcastCharactCallback.receive";

    public static final String HAP_SCHEME = "homekit";
    public static String URI_FORMAT = HAP_SCHEME + "://com.guok/homekit?target=%s&object=%s&method=%s&value=%s&subscribe=%s";
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
    public int setValueCallback(T value, boolean subscribe) {
        Log.d("GK", "setValueCallback sendBroadcast!" + value);
        String s = String.format(URI_FORMAT,
                queryEscapers.escape(this.target),
                queryEscapers.escape(this.object),
                queryEscapers.escape("set"),
                queryEscapers.escape(value.toString()),
                queryEscapers.escape(subscribe + ""));
        Intent intent = new Intent(SEND_ACTION, Uri.parse(s));
        mContext.sendBroadcast(intent);
        return HapStatusCodes.SUCCESS;
    }

    @Override
    public ListenableFuture<T> getValueCallback(BaseCharacteristic<T> characteristic, boolean subscribe, FetchCallBack<T> callBack) {
        Log.d("GK", "getValueCallback sendBroadcast!");
        mFetchCallBack = callBack;
        sLatch = new CountDownLatch(1);
        String s = String.format(URI_FORMAT,
                queryEscapers.escape(this.target),
                queryEscapers.escape(this.object),
                queryEscapers.escape("get"),
                queryEscapers.escape(""),
                queryEscapers.escape(subscribe + ""));
        Intent intent = new Intent(SEND_ACTION, Uri.parse(s));
        mContext.sendBroadcast(intent);
        try {
            sLatch.await(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return characteristic.getValueImmediately();
    }


    class HapReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && BroadcastCharactCallback.RECEIVE_ACTION.equals(intent.getAction())) {
                String value1 = intent.getStringExtra(target + object);
                if (value1 == null) return;
                HapValueVO vo = JSON.parseObject(value1, HapValueVO.class);
                Log.d("GK", "HapReceiver get response: " + vo.toString());
                if (mFetchCallBack != null) {
                    T v = clazzCast(vo);
                    if (v != null)
                        mFetchCallBack.fetchValue(v);
                }
                if (sLatch != null) sLatch.countDown();
            }
        }

        private T clazzCast(HapValueVO vo) {
            Object value = vo.getValue();
            Class type = getSingleGenericType(BroadcastCharactCallback.this);
            if (value.getClass().getName().equals(type.getName())) {
                return (T) value;
            }
            if (Number.class.isInstance(value) && Number.class.isAssignableFrom(type)) {
                String clazzName = type.getSimpleName().toLowerCase();
                try {
                    Method method = Number.class.getDeclaredMethod(clazzName + "Value", null);
                    try {
                        Object o = method.invoke((Number) value, null);
                        return (T) o;
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
