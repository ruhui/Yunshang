package com.shidai.yunshang.managers;

import com.shidai.yunshang.networks.ApiClient;
import com.shidai.yunshang.networks.ResponseParent;
import com.shidai.yunshang.networks.ZZCHeaders;
import com.shidai.yunshang.networks.requests.LoginRequest;
import com.shidai.yunshang.networks.responses.LoginResponse;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
    *
    * 创建作者： 黄如辉
    * 创建时间： 2017/4/10 11:57
   **/

public class UserManager {


    /**
     * 众众车登录接口
     */
    public static void loginzzc(String teltphone, String password, Subscriber<ResponseParent<LoginResponse>> subscriber){
        LoginRequest loginResult = new LoginRequest(teltphone,password);

        ZZCHeaders zzcHeaders = new ZZCHeaders(loginResult);
        ApiClient.getApiService().login(loginResult, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
