package com.guok.hapandroid;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.haierubic.os.homekitdemo.daemon.AbsWorkService;
import com.haierubic.os.homekitdemo.daemon.DaemonEnv;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author guok
 */

public class HapMainService extends AbsWorkService {

    BridgeImpl mBridge;

    //是否 任务完成, 不再需要服务运行?
    public static boolean sShouldStopService;
    ExecutorService executor = Executors.newSingleThreadExecutor();

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
        registerReceiver(new HapReveiver(this), new IntentFilter(HapReveiver.ACTION_HAP));
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
        mBridge = new BridgeImpl();

        executor.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                mBridge.registerHap(HapMainService.this);
                return null;
            }
        });
    }

    @Override
    public void stopWork(Intent intent, int flags, int startId) {
        mBridge.stop();
        stopService();
    }

    /**
     * 任务是否正在运行?
     *
     * @return 任务正在运行, true; 任务当前不在运行, false; 无法判断, 什么也不做, null.
     */
    @Override
    public Boolean isWorkRunning(Intent intent, int flags, int startId) {
        return mBridge != null && mBridge.getBridge() != null;
    }

    @Override
    public IBinder onBind(Intent intent, Void v) {
        return null;
    }

    @Override
    public void onServiceKilled(Intent rootIntent) {

    }

    public BridgeImpl getBridge() {
        return mBridge;
    }
}
