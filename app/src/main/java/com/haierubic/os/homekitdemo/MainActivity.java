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

import com.haierubic.os.homekitdemo.daemon.IntentWrapper;

import java.util.List;
import java.util.Map;

import io.netty.handler.codec.http.QueryStringDecoder;

public class MainActivity extends AppCompatActivity {

    private HomekitReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BroadcastCharactCallback.setContext(this);

        mReceiver = new HomekitReceiver();
        IntentFilter filter = new IntentFilter(BroadcastCharactCallback.ACTION);
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

    public static class HomekitReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && BroadcastCharactCallback.ACTION.equals(intent.getAction())) {
                Uri data = intent.getData();
                if (data != null && data.getScheme().equals("homekit")) {
                    QueryStringDecoder decoder = new QueryStringDecoder(data.toString());
                    for (Map.Entry<String, List<String>> entry : decoder.parameters().entrySet()) {
                        Log.i("GK", "homekit broadcast receive: " + entry.getKey() + "=" + entry.getValue().get(0));
                    }
                }
            }
        }
    }
}
