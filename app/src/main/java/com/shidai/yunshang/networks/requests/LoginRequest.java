package com.shidai.yunshang.networks.requests;

/**
 * 描述：众众车登录请求参数
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/14 15:25
 **/
public class LoginRequest {
    public String mobile;
    public String password;

    public LoginRequest(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }
}
