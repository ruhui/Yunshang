package com.shidai.yunshang.models;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/9 11:06
 **/
public class BillModel {
    private String order_no;
    private int id;
    private int type;
    private String name;
    private double amount;
    private String time;
    private String status_name;

    public BillModel(String order_no, int id, int type, String name, double amount, String time, String status_name) {
        this.order_no = order_no;
        this.id = id;
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.time = time;
        this.status_name = status_name;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }
}
//"order_no": "sample string 1",
//        "id": 2,
//        "type": 0,
//        "name": "sample string 3",
//        "amount": 4,
//        "time": "sample string 5",
//        "status_name": "sample string 6"
