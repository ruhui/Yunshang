package com.shidai.yunshang.models;

import java.util.List;

/**
 * 创建时间： 2017/7/28.
 * 作者：黄如辉
 * 功能描述：
 */

public class BillbagModel {
    private String name;
    private String code;
    private boolean isClick;
    private List<ChannelModel> channel;

    public BillbagModel(String name, String code, List<ChannelModel> channel) {
        this.name = name;
        this.code = code;
        this.channel = channel;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ChannelModel> getChannel() {
        return channel;
    }

    public void setChannel(List<ChannelModel> channel) {
        this.channel = channel;
    }
}
//"name": "sample string 1",
//        "code": "sample string 2",
//        "channel":