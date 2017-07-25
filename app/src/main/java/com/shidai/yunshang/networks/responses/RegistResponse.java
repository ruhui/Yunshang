package com.shidai.yunshang.networks.responses;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/7/25 16:19
 **/
public class RegistResponse {
    public String access_token;
    public String expires_date;

    public RegistResponse(String access_token, String expires_date) {
        this.access_token = access_token;
        this.expires_date = expires_date;
    }
}
//"access_token": "sample string 3",
//        "expires_date": "2017-07-25 16:09:26"