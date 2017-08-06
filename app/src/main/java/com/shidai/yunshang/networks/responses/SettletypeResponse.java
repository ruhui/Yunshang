package com.shidai.yunshang.networks.responses;

import com.shidai.yunshang.utils.Tool;

/**
 * 创建时间： 2017/8/6.
 * 作者：黄如辉
 * 功能描述：
 */

public class SettletypeResponse {
    private String name;
    private boolean is_open;
    private double rate;
    private String settle_type;
    private double single_quota;
    private double card_quota;
    private boolean isClick = false;

    public SettletypeResponse(){}

    public SettletypeResponse(String name, boolean is_open, double rate, String settle_type, double single_quota, double card_quota) {
        this.name = name;
        this.is_open = is_open;
        this.rate = rate;
        this.settle_type = settle_type;
        this.single_quota = single_quota;
        this.card_quota = card_quota;
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

    public boolean is_open() {
        return is_open;
    }

    public void setIs_open(boolean is_open) {
        this.is_open = is_open;
    }

    public void setSettletypeResponse(SettletypeResponse response){
        setName(response.getName());
        setIs_open(response.is_open);
        setRate(response.getRateDouble());
        setSettle_type(response.getSettle_type());
        setSingle_quota(response.getSingleQuota());
        setCard_quota(response.getCardQuota());

    }

    public double getRateDouble() {
        return rate;
    }

    public String getRate() {
        String fee ;//费率
        fee = Tool.formatPrice(rate * 100) + "%";
        return fee;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getSettle_type() {
        return settle_type;
    }

    public void setSettle_type(String settle_type) {
        this.settle_type = settle_type;
    }

    public double getSingleQuota() {
        return single_quota;
    }

    public String getSingle_quota() {
        String singQuota = "";
        if (single_quota == 0){
            singQuota = "不限";
        }else{
            singQuota = "¥"+Tool.formatPrice(single_quota) + "/笔";
        }
        return singQuota;
    }

    public void setSingle_quota(double single_quota) {
        this.single_quota = single_quota;
    }

    public double getCardQuota() {
        return card_quota;
    }

    public String getCard_quota() {
        String cardQuota = "";//单卡额度/天,0不限
        if (card_quota == 0){
            cardQuota = "不限";
        }else{
            cardQuota = Tool.formatPrice(card_quota) + "万/天";
        }
        return cardQuota;
    }

    public void setCard_quota(double card_quota) {
        this.card_quota = card_quota;
    }
}
// "name": "sample string 1",
//         "is_open": true,
//         "rate": 3,
//         "settle_type": "sample string 4",
//         "single_quota": 5,
//         "card_quota": 6
