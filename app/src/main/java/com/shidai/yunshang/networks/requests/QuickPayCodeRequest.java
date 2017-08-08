package com.shidai.yunshang.networks.requests;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/8 16:43
 **/
public class QuickPayCodeRequest {
    public String sms_code;

    public QuickPayCodeRequest(String sms_code) {
        this.sms_code = sms_code;
    }
}
