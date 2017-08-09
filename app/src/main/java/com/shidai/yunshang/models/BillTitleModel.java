package com.shidai.yunshang.models;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/9 11:25
 **/
public class BillTitleModel {
    private String order_no;
    private String date;
    private double total;

    public BillTitleModel(String order_no, String date, double total) {
        this.order_no = order_no;
        this.date = date;
        this.total = total;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
