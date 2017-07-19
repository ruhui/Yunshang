package com.shidai.yunshang.networks.responses;

/**
 * 描述：众众车登录返回
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/14 15:59
 **/
public class LoginResponse {

    private String access_token;
    private String expires_date;

    public LoginResponse(String access_token, String expires_date) {
        this.access_token = access_token;
        this.expires_date = expires_date;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_date() {
        return expires_date;
    }

    public void setExpires_date(String expires_date) {
        this.expires_date = expires_date;
    }
}
// "access_token": "sample string 3",
//         "expires_date": "2017-07-19 22:38:25"