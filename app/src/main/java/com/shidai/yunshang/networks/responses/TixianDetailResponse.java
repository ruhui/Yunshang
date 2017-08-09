package com.shidai.yunshang.networks.responses;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/9 14:14
 **/
public class TixianDetailResponse {
    private String order_no;
    private double transfer_amount;
    private double amount;
    private double settle_amount;
    private int status;
    private String apply_time;
    private String pay_name;

    public TixianDetailResponse(String order_no, double transfer_amount, double amount, double settle_amount, int status, String apply_time, String pay_name) {
        this.order_no = order_no;
        this.transfer_amount = transfer_amount;
        this.amount = amount;
        this.settle_amount = settle_amount;
        this.status = status;
        this.apply_time = apply_time;
        this.pay_name = pay_name;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public double getTransfer_amount() {
        return transfer_amount;
    }

    public void setTransfer_amount(double transfer_amount) {
        this.transfer_amount = transfer_amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getSettle_amount() {
        return settle_amount;
    }

    public void setSettle_amount(double settle_amount) {
        this.settle_amount = settle_amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getApply_time() {
        return apply_time;
    }

    public void setApply_time(String apply_time) {
        this.apply_time = apply_time;
    }
}
//"order_no": "sample string 1",
//        "transfer_amount": 2.0,
//        "amount": 3.0,
//        "settle_amount": 4.0,
//        "status": 0,
//        "apply_time": "2017-08-09 14:14:37"
