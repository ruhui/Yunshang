package com.shidai.yunshang.networks.requests;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/8 14:03
 **/
public class SelectchannelRequest {
    public String pay_channel;
    public String settle_type;

    public SelectchannelRequest(String pay_channel, String settle_type) {
        this.pay_channel = pay_channel;
        this.settle_type = settle_type;
    }
}
