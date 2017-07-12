package com.haierubic.os.sample;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.guok.hapandroid.BroadcastCharactCallback;
import com.guok.hapandroid.HapMainService;
import com.guok.hapandroid.HapReveiver;
import com.guok.hapandroid.HapValueVO;
import com.guok.hapandroid.PreferencesUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.netty.handler.codec.http.QueryStringDecoder;

public class MainActivity extends AppCompatActivity {

    private HomekitReceiver mReceiver;

    private boolean power = true;
    private int volume = 2;

    private Map<String, Boolean> subscribes = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BroadcastCharactCallback.setContext(this);

        mReceiver = new HomekitReceiver();
        IntentFilter filter = new IntentFilter(BroadcastCharactCallback.SEND_ACTION);
        filter.addDataScheme("homekit");
        registerReceiver(mReceiver, filter);

        try {
            startService(new Intent(this, HapMainService.class));
        } catch (Exception ignored) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    public void clearSP(View view) {
        PreferencesUtil.clear(this);
    }

    public void reStart(View view) {
        sendBroadcast(new Intent(HapReveiver.ACTION_HAP));
    }

    //防止华为机型未加入白名单时按返回键回到桌面再锁屏后几秒钟进程被杀
    @Override
    public void onBackPressed() {
        com.haierubic.os.homekitdemo.daemon.IntentWrapper.onBackPressed(this);
    }


    public void powerTaggle(View view) {
        setPower(!this.power);
    }

    public void volumeIncrease(View view) {
        setVolume(++this.volume);
    }


    public boolean isPower() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
        Boolean subs = subscribes.get("power");
        if (subs != null && subs) {
            HapValueVO<Boolean> vo = new HapValueVO<>();
            vo.setObject("power");
            vo.setValue(this.power);
            vo.setTarget("player");
            vo.setSubscribe(true);
            sendToHomekit(vo);
        }
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
        Boolean subs = subscribes.get("volume");
        if (subs != null && subs) {
            HapValueVO<Integer> vo = new HapValueVO<>();
            vo.setObject("volume");
            vo.setValue(this.volume);
            vo.setTarget("player");
            vo.setSubscribe(true);
            sendToHomekit(vo);
        }
    }

    public class HomekitReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && BroadcastCharactCallback.SEND_ACTION.equals(intent.getAction())) {
                Uri data = intent.getData();
                if (data != null && data.getScheme().equals(BroadcastCharactCallback.HAP_SCHEME)) {
                    QueryStringDecoder decoder = new QueryStringDecoder(data.toString());
                    for (Map.Entry<String, List<String>> entry : decoder.parameters().entrySet()) {
                        Log.i("GK", "homekit broadcast receive: " + entry.getKey() + "=" + entry.getValue().get(0));
                    }

                    List<String> target = decoder.parameters().get("target");
                    if (target == null || target.size() == 0 || !target.get(0).equals("player"))
                        return;

                    List<String> methods = decoder.parameters().get("method");
                    if (methods == null || methods.size() == 0 || methods.get(0).length() == 0)
                        return;

                    if (methods.get(0).equals("get"))
                        handleGetRequest(decoder);
                    else if (methods.get(0).equals("set")) {
                        handleSetRequest(decoder);
                    }

                    List<String> subscribe = decoder.parameters().get("subscribe");
                    if (subscribe != null && subscribe.size() > 0 && subscribe.get(0).length() > 0) {
                        String s = subscribe.get(0);
                        List<String> object = decoder.parameters().get("object");
                        if (object != null && object.size() > 0 && object.get(0).length() > 0) {
                            String obj = object.get(0);
                            if ("true".equals(s) || "1".equals(s) || "false".equals(s) || "0".equals(s)) {
                                subscribes.put(obj, Boolean.valueOf(s));
                            }
                        }
                    }

                }
            }
        }

        private void handleSetRequest(QueryStringDecoder decoder) {

            List<String> object = decoder.parameters().get("object");
            if (object == null || object.size() == 0 || object.get(0).length() == 0)
                return;

            List<String> value = decoder.parameters().get("value");
            if (value != null && value.size() > 0 && value.get(0).length() > 0) {
                if (object.get(0).equals("power")) {
                    MainActivity.this.power = Boolean.valueOf(value.get(0));
                } else if (object.get(0).equals("volume")) {
                    MainActivity.this.volume = Integer.valueOf(value.get(0));
                }
                System.out.println("MainActivity set player  " + object.get(0) + ": " + value.get(0));
            }
        }

        private HapValueVO handleGetRequest(QueryStringDecoder decoder) {
            HapValueVO valueVO = genHapValueVO(decoder);
            if (valueVO == null) {
                System.err.println("HomekitReceiver receives wrong request!");
                return null;
            }
            List<String> targets = decoder.parameters().get("target");
            if (targets != null && targets.size() > 0 && targets.get(0).length() > 0)
                valueVO.setTarget(targets.get(0));
            else {
                return null;
            }

            sendToHomekit(valueVO);
            return valueVO;
        }

        private HapValueVO genHapValueVO(QueryStringDecoder decoder) {
            List<String> objects = decoder.parameters().get("object");
            if (objects != null && objects.size() > 0 && objects.get(0).length() > 0) {
                String s = objects.get(0);
                if ("power".equals(s)) {
                    HapValueVO<Boolean> vo = new HapValueVO<>();
                    vo.setObject(s);
                    vo.setValue(power);
                    return vo;
                } else if ("volume".equals(s)) {
                    HapValueVO<Integer> vo = new HapValueVO<>();
                    vo.setObject(s);
                    vo.setValue(volume);
                    return vo;
                }
            }
            return null;
        }
    }

    private void sendToHomekit(HapValueVO valueVO) {
        String json = JSON.toJSONString(valueVO);
        Intent sender = new Intent(BroadcastCharactCallback.RECEIVE_ACTION);
        sender.putExtra(valueVO.getTarget() + valueVO.getObject(), json);
        MainActivity.this.sendBroadcast(sender);
        System.out.println("MainActivity send player powser: " + json);
    }
}

