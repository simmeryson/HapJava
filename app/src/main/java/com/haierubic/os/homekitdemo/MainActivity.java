package com.haierubic.os.homekitdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.haierubic.os.homekitdemo.daemon.IntentWrapper;

import java.util.List;
import java.util.Map;

import io.netty.handler.codec.http.QueryStringDecoder;

public class MainActivity extends AppCompatActivity {

    private HomekitReceiver mReceiver;

    private boolean power = true;

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
        IntentWrapper.onBackPressed(this);
    }

    public  class HomekitReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && BroadcastCharactCallback.SEND_ACTION.equals(intent.getAction())) {
                Uri data = intent.getData();
                if (data != null && data.getScheme().equals(BroadcastCharactCallback.HAP_SCHEME)) {
                    QueryStringDecoder decoder = new QueryStringDecoder(data.toString());
                    for (Map.Entry<String, List<String>> entry : decoder.parameters().entrySet()) {
                        Log.i("GK", "homekit broadcast receive: " + entry.getKey() + "=" + entry.getValue().get(0));
                    }

                    List<String> methods = decoder.parameters().get("method");
                    if (methods == null ||  methods.size() == 0)
                        return;

                    if (methods.get(0).equals("get"))
                        sendResponseOfGet(decoder);
                    if (methods.get(0).equals("set")) {
                        List<String> target = decoder.parameters().get("target");
                        if (target == null || target.size() == 0 || !target.get(0).equals("player")) return;
                        List<String> object = decoder.parameters().get("object");
                        if (object == null || object.size() == 0 || !object.get(0).equals("power")) return;
                        List<String> value = decoder.parameters().get("value");
                        if (value != null && value.size() > 0) {
                            MainActivity.this.power = Boolean.valueOf(value.get(0));
                            System.out.println("MainActivity set player powser: "+ MainActivity.this.power);
                        }
                    }

                }
            }
        }

        private void sendResponseOfGet(QueryStringDecoder decoder) {
            HapValueVO<Boolean> valueVO = new HapValueVO<>();
            List<String> targets = decoder.parameters().get("target");
            if (targets != null && targets.size() > 0)
                valueVO.setTarge(targets.get(0));
            List<String> objects = decoder.parameters().get("object");
            if (objects != null && objects.size() > 0)
                valueVO.setObject(objects.get(0));
            valueVO.setValue(power);

            String json = new Gson().toJson(valueVO);
            Intent sender = new Intent(BroadcastCharactCallback.RECEIVE_ACTION);
            sender.putExtra("value", json);
            MainActivity.this.sendBroadcast(sender);
            System.out.println("MainActivity send player powser: "+ json);
        }
    }
}
