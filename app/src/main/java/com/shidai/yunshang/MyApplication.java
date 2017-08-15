package com.shidai.yunshang;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.shidai.yunshang.constants.Constant;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;

/**
 *
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/18 11:42
 **/

public class MyApplication extends Application {

    private boolean tipsShow = false;

    private static MyApplication mInstance;
    private Context appContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this) ;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //集成测试，正式试用时要设为false
        MobclickAgent.setDebugMode(true);
        MobclickAgent.UMAnalyticsConfig config = new MobclickAgent.UMAnalyticsConfig( mInstance, Constant.UMENGAPPKEY, "yingyongbao");
        MobclickAgent. startWithConfigure(config);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mInstance = null;
    }

    public void setTipShow(boolean tipsShow) {
        this.tipsShow = tipsShow;
    }

    public boolean getTipShow(){
        return tipsShow;
    }
}
