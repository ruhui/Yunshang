package com.shidai.yunshang.managers;

import com.shidai.yunshang.networks.ResponseParent;
import com.shidai.yunshang.networks.requests.LoginRequest;
import com.shidai.yunshang.networks.responses.LoginResponse;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
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
}
