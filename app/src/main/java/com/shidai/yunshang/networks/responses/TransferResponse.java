package com.shidai.yunshang.networks.responses;

import java.io.Serializable;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/7 9:54
 **/
public class TransferResponse implements Serializable{
    private String account_name;//商户名
    private String mobile;//手机号(UI叫账号)
    private double transfer_amount;//提现金额
    private double settle_amount;//结算费
    private double amount;//实际到账
    private String account_no;//到账账号
    private String apply_time;//提现时间

    public TransferResponse(String account_name, String mobile, double transfer_amount, double settle_amount, double amount, String account_no, String apply_time) {
        this.account_name = account_name;
        this.mobile = mobile;
        this.transfer_amount = transfer_amount;
        this.settle_amount = settle_amount;
        this.amount = amount;
        this.account_no = account_no;
        this.apply_time = apply_time;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public double getTransfer_amount() {
        return transfer_amount;
    }

    public void setTransfer_amount(double transfer_amount) {
        this.transfer_amount = transfer_amount;
    }

    public double getSettle_amount() {
        return settle_amount;
    }

    public void setSettle_amount(double settle_amount) {
        this.settle_amount = settle_amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getApply_time() {
        return apply_time;
    }

    public void setApply_time(String apply_time) {
        this.apply_time = apply_time;
    }
}
//"account_name": "sample string 1",
//        "mobile": "sample string 2",
//        "transfer_amount": 3.0,
//        "settle_amount": 4.0,
//        "amount": 5.0,
//        "account_no": "sample string 6",
//        "apply_time": "2017-08-07 09:52:24"