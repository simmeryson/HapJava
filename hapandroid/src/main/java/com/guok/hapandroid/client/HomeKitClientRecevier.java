package com.guok.hapandroid.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.guok.hapandroid.HapValueVO;
import com.guok.hapandroid.server.BroadcastCharactCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.netty.handler.codec.http.QueryStringDecoder;


/**
 * @author guok
 */

public class HomeKitClientRecevier extends BroadcastReceiver {

    public static final String TAG = HomeKitClientRecevier.class.getSimpleName();
    public static final String SETUP_ACTION = "com.guok.hapjava.server.setup";
    public static final String SETUP_KEY = "setup";

    private final Map<String, HomeKitControl> listeners = new HashMap<>();
    private static Context mContext;
    private IServerSetupListener mSetupListener;

    public HomeKitClientRecevier(Context context) {
        mContext = context.getApplicationContext();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && BroadcastCharactCallback.SEND_ACTION.equals(intent.getAction())) {
            Uri data = intent.getData();
            if (data != null && data.getScheme().equals(BroadcastCharactCallback.HAP_SCHEME)) {
                QueryStringDecoder decoder = new QueryStringDecoder(data.toString());
                for (Map.Entry<String, List<String>> entry : decoder.parameters().entrySet()) {
                    Log.d(TAG, "homekit broadcast receive: " + entry.getKey() + "=" + entry.getValue().get(0));
                }

                List<String> domains = decoder.parameters().get(BroadcastCharactCallback.HAP_DOMAIN);
                if (domains == null || domains.size() == 0 || TextUtils.isEmpty(domains.get(0)))
                    return;

                String domain = domains.get(0);

                List<String> methods = decoder.parameters().get("method");
                if (methods == null || methods.size() == 0 || methods.get(0).length() == 0)
                    return;

                if (methods.get(0).equals("get"))
                    handleGetRequest(domain, decoder);
                else if (methods.get(0).equals("set")) {
                    handleSetRequest(domain, decoder);
                }

                List<String> subscribe = decoder.parameters().get("subscribe");
                if (subscribe != null && subscribe.size() > 0 && subscribe.get(0).length() > 0) {
                    String s = subscribe.get(0);
                    List<String> target = decoder.parameters().get(BroadcastCharactCallback.HAP_TARGET);
                    if (target != null && target.size() > 0 && target.get(0).length() > 0) {
                        String obj = target.get(0);
                        if ("true".equals(s) || "1".equals(s) || "false".equals(s) || "0".equals(s)) {
                            HomeKitControl listener = listeners.get(domains.get(0));
                            if (listener != null)
                                listener.subscribe(obj, Boolean.valueOf(s), new HomeKitControl.SubscribeCallback() {
                                    @Override
                                    public void change(HapValueVO value) {
                                        sendToHomekit(value);
                                    }
                                });
                            Log.d(TAG, "subscribe  " + target.get(0) + ": " + s);
                        }
                    }
                }
            }
        } else if (intent != null && SETUP_ACTION.equals(intent.getAction())) {
            boolean booleanExtra = intent.getBooleanExtra(SETUP_KEY, false);
            if (mSetupListener != null)
                mSetupListener.serverSetup(booleanExtra);
        }
    }

    private void handleSetRequest(String domain, QueryStringDecoder decoder) {
        if (TextUtils.isEmpty(domain))
            return;

        List<String> targets = decoder.parameters().get(BroadcastCharactCallback.HAP_TARGET);
        if (targets == null || targets.size() == 0 || TextUtils.isEmpty(targets.get(0)))
        return;

        List<String> value = decoder.parameters().get(BroadcastCharactCallback.HAP_VALUE);
        if (value != null && value.size() > 0 && value.get(0).length() > 0) {
            HomeKitControl listener = listeners.get(domain);
            if (listener != null)
                listener.setValue(targets.get(0), value.get(0));
            Log.d(TAG, "set player  " + targets.get(0) + ": " + value.get(0));
        }
    }


    private void handleGetRequest(String domain, QueryStringDecoder decoder) {
        if (TextUtils.isEmpty(domain))
            return;

        List<String> targets = decoder.parameters().get(BroadcastCharactCallback.HAP_TARGET);
        if (targets == null || targets.size() == 0 || TextUtils.isEmpty(targets.get(0)))
            return;

        HomeKitControl listener = listeners.get(domain);
        if (listener != null) {
            HapValueVO valueVO = listener.getValue(targets.get(0));
            if (valueVO != null) {
                valueVO.setDomain(domain);
                sendToHomekit(valueVO);
            }
        }
    }

    public static void sendToHomekit(HapValueVO valueVO) {
        if (valueVO != null && valueVO.getDomain() != null && valueVO.getTarget() != null) {
            String json = JSON.toJSONString(valueVO);
            Intent sender = new Intent(BroadcastCharactCallback.RECEIVE_ACTION);
            sender.putExtra(valueVO.getDomain() + valueVO.getTarget(), json);
            mContext.sendBroadcast(sender);
            Log.d(TAG, "HomeKitClientRecevier send : " + json);
        }
    }

    public HomeKitControl getListeners(String target) {
        if (target != null && target.length() > 0)
            return this.listeners.get(target);
        return null;
    }

    public void addListener(String target, HomeKitControl listener) {
        if (target != null && target.length() > 0)
            this.listeners.put(target, listener);
    }

    public IServerSetupListener getSetupListener() {
        return mSetupListener;
    }

    public void setSetupListener(IServerSetupListener setupListener) {
        mSetupListener = setupListener;
    }
}
