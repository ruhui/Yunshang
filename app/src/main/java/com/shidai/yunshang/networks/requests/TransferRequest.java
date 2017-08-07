package com.shidai.yunshang.networks.requests;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/7 9:52
 **/
public class TransferRequest {
    private double amount;
    private String settle_type;
    private int type;

    public TransferRequest(double amount, String settle_type, int type) {
        this.amount = amount;
        this.settle_type = settle_type;
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSettle_type() {
        return settle_type;
    }

    public void setSettle_type(String settle_type) {
        this.settle_type = settle_type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
//"amount": 2.0,
//        "settle_type": "sample string 3",
//        "type": 1 1余额，2分润