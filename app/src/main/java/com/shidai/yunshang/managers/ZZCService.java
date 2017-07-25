package com.shidai.yunshang.managers;

import com.shidai.yunshang.networks.ResponseParent;
import com.shidai.yunshang.networks.requests.LoginRequest;
import com.shidai.yunshang.networks.requests.RegistRequest;
import com.shidai.yunshang.networks.requests.SendRegsmsRequest;
import com.shidai.yunshang.networks.responses.BulletinResponse;
import com.shidai.yunshang.networks.responses.LoginResponse;
import com.shidai.yunshang.networks.responses.RegistResponse;
import com.shidai.yunshang.networks.responses.SystemResponse;
import com.shidai.yunshang.networks.responses.UsermsgResponse;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/14 15:57
 **/
public interface ZZCService {
    /*登录*/
    @POST("account/login")
    Observable<ResponseParent<LoginResponse>> login(@Body LoginRequest httpClient, @HeaderMap Map<String, String> header);

    /*注册*/
    @POST("account/register")
    Observable<ResponseParent<RegistResponse>> register(@Body RegistRequest httpClient, @HeaderMap Map<String, String> header);

    /*获取验证码*/
    @POST("common/regsms")
    Observable<ResponseParent<Boolean>> getregsms(@Body SendRegsmsRequest sendCode, @HeaderMap Map<String, String> header);

    /*获取我的资料*/
    @GET("account/user")
    Observable<ResponseParent<UsermsgResponse>> getusermsg(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*公告总数和第一条*/
    @GET("msg/bulletin")
    Observable<ResponseParent<BulletinResponse>> getbulletin(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*系统消息*/
    @GET("msg/system")
    Observable<ResponseParent<SystemResponse>> getsystemmsg(@QueryMap  Map<String, String> hashMap, @HeaderMap Map<String, String> header);

}
