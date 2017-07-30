package com.shidai.yunshang.models;

/**
 * 创建时间： 2017/7/28.
 * 作者：黄如辉
 * 功能描述：
 */

public class ChannelModel {
    private int pay_channel;
    private String name;
    private String settle_type;
    private double fee;
    private double settle;
    private double single_quota;
    private double card_quota;
    private String reduce;

    public ChannelModel(int pay_channel, String name, String settle_type, double fee, double settle, double single_quota, double card_quota, String reduce) {
        this.pay_channel = pay_channel;
        this.name = name;
        this.settle_type = settle_type;
        this.fee = fee;
        this.settle = settle;
        this.single_quota = single_quota;
        this.card_quota = card_quota;
        this.reduce = reduce;
    }

    public int getPay_channel() {
        return pay_channel;
    }

    public void setPay_channel(int pay_channel) {
        this.pay_channel = pay_channel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSettle_type() {
        return settle_type;
    }

    public void setSettle_type(String settle_type) {
        this.settle_type = settle_type;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getSettle() {
        return settle;
    }

    public void setSettle(double settle) {
        this.settle = settle;
    }

    public double getSingle_quota() {
        return single_quota;
    }

    public void setSingle_quota(double single_quota) {
        this.single_quota = single_quota;
    }

    public double getCard_quota() {
        return card_quota;
    }

    public void setCard_quota(double card_quota) {
        this.card_quota = card_quota;
    }

    public String getReduce() {
        return reduce;
    }

    public void setReduce(String reduce) {
        this.reduce = reduce;
    }
}
// "pay_channel": 0,
//         "name": "sample string 1",
//         "settle_type": "sample string 2",
//         "fee": 3.0,
//         "settle": 4.0,
//         "single_quota": 5.0,
//         "card_quota": 6.0,
//         "reduce": "sample string 7"
