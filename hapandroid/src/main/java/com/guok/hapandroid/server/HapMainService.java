package com.guok.hapandroid.server;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.guok.hapandroid.PreferencesUtil;
import com.guok.hapandroid.daemon.AbsWorkService;
import com.guok.hapandroid.daemon.DaemonEnv;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author guok
 */

public class HapMainService extends AbsWorkService {

    private static volatile BridgeServer sBridgeServer;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    //是否 任务完成, 不再需要服务运行?
    public static boolean sShouldStopService;

    public static void stopService() {
        //我们现在不再需要服务运行了, 将标志位置为 true
        sShouldStopService = true;
        //取消对任务的订阅
        cancelJobAlarmSub();
    }

    @Override
    public void onCreate() {
        DaemonEnv.initialize(this, HapMainService.class, DaemonEnv.DEFAULT_WAKE_UP_INTERVAL);
        super.onCreate();
        registerReceiver(new HapServerReveiver(this), new IntentFilter(HapServerReveiver.ACTION_HAP));
        PreferencesUtil.setContext(this);
    }

    /**
     * 是否 任务完成, 不再需要服务运行?
     *
     * @return 应当停止服务, true; 应当启动服务, false; 无法判断, 什么也不做, null.
     */
    @Override
    public Boolean shouldStopService(Intent intent, int flags, int startId) {
        return sShouldStopService;
    }

    @Override
    public void startWork(Intent intent, int flags, int startId) {
        sBridgeServer = new BridgeServer();

        executor.submit(new Runnable() {
            @Override
            public void run() {
                sBridgeServer.registerHap(HapMainService.this);
            }
        });
    }

    @Override
    public void stopWork(Intent intent, int flags, int startId) {
        sBridgeServer.stop();
        stopService();
        sBridgeServer = null;
    }

    /**
     * 任务是否正在运行?
     *
     * @return 任务正在运行, true; 任务当前不在运行, false; 无法判断, 什么也不做, null.
     */
    @Override
    public Boolean isWorkRunning(Intent intent, int flags, int startId) {
        return sBridgeServer != null;
    }

    @Override
    public IBinder onBind(Intent intent, Void v) {
        return null;
    }

    @Override
    public void onServiceKilled(Intent rootIntent) {

    }

    public static BridgeServer getBridge() {
        return sBridgeServer;
    }
}
