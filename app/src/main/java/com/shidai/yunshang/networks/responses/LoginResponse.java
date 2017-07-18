package com.shidai.yunshang.networks.responses;

/**
 * 描述：众众车登录返回
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/14 15:59
 **/
public class LoginResponse {

    public String access_token;
    public String expires_date;

    public LoginResponse(String access_token, String expires_date) {
        this.access_token = access_token;
        this.expires_date = expires_date;
    }
}
