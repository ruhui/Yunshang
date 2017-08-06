package com.shidai.yunshang.networks.requests;

/**
 * 创建时间： 2017/8/5.
 * 作者：黄如辉
 * 功能描述：
 */

public class RefreshUserResquest {
    public String access_token;

    public RefreshUserResquest(String access_token) {
        this.access_token = access_token;
    }
}
