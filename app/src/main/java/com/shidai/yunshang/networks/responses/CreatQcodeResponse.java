package com.shidai.yunshang.networks.responses;

import java.io.Serializable;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/11 11:20
 **/
public class CreatQcodeResponse implements Serializable{
    private String native_url;
    private String username;
    private double amount;

    public CreatQcodeResponse(String native_url, String username, double amount) {
        this.native_url = native_url;
        this.username = username;
        this.amount = amount;
    }

    public String getNative_url() {
        return native_url;
    }

    public void setNative_url(String native_url) {
        this.native_url = native_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
//"native_url": "sample string 1",
//        "username": "sample string 2",
//        "amount": 3.0