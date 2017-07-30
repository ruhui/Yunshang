package com.shidai.yunshang.networks.responses;

import com.shidai.yunshang.models.BillbagModel;

import java.io.Serializable;
import java.util.List;

/**
 * 创建时间： 2017/7/28.
 * 作者：黄如辉
 * 功能描述：
 */

public class ShowupResponse implements Serializable{
    private int id;
    private String name;
    private double up_fee;
    private boolean is_online;
    private boolean is_show;
    private String remark;
    private List<BillbagModel> payments;

    public ShowupResponse(int id, String name, double up_fee, boolean is_online, boolean is_show, String remark, List<BillbagModel> payments) {
        this.id = id;
        this.name = name;
        this.up_fee = up_fee;
        this.is_online = is_online;
        this.is_show = is_show;
        this.remark = remark;
        this.payments = payments;
    }

    public boolean is_show() {
        return is_show;
    }

    public void setIs_show(boolean is_show) {
        this.is_show = is_show;
    }

    public boolean is_online() {
        return is_online;
    }

    public void setIs_online(boolean is_online) {
        this.is_online = is_online;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUp_fee() {
        return up_fee;
    }

    public void setUp_fee(double up_fee) {
        this.up_fee = up_fee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<BillbagModel> getPayments() {
        return payments;
    }

    public void setPayments(List<BillbagModel> payments) {
        this.payments = payments;
    }
}
//"id": 1,
//        "name": "sample string 2",
//        "up_fee": 3.0,
//        "is_online": true,
//        "remark": "sample string 5",
//        "payments":