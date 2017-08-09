package com.shidai.yunshang.networks.responses;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/9 18:05
 **/
public class ShoukuanDetailResponse {
    private String order_no;
    private String order_time;
    private String pay_name;
    private double order_amount;
    private double amount;
    private double pay_amount;
    private double fee_amount;
    private int status;

    public ShoukuanDetailResponse(String order_no, String order_time, String pay_name, double order_amount, double amount, double pay_amount, double fee_amount, int status) {
        this.order_no = order_no;
        this.order_time = order_time;
        this.pay_name = pay_name;
        this.order_amount = order_amount;
        this.amount = amount;
        this.pay_amount = pay_amount;
        this.fee_amount = fee_amount;
        this.status = status;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public double getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(double order_amount) {
        this.order_amount = order_amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(double pay_amount) {
        this.pay_amount = pay_amount;
    }

    public double getFee_amount() {
        return fee_amount;
    }

    public void setFee_amount(double fee_amount) {
        this.fee_amount = fee_amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
//"order_no": "sample string 1",
//        "order_time": "2017-08-09 17:35:55",
//        "pay_name": "sample string 3",
//        "order_amount": 4.0,
//        "amount": 5.0,
//        "pay_amount": 6.0,
//        "fee_amount": 7.0,
//        "status": 0