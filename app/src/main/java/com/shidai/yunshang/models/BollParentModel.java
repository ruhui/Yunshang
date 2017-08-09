package com.shidai.yunshang.models;

import java.util.List;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/9 11:05
 **/
public class BollParentModel {
    private String date;
    private double total;
    private List<BillModel> items;

    public BollParentModel(String date, double total, List<BillModel> items) {
        this.date = date;
        this.total = total;
        this.items = items;
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

    public List<BillModel> getItems() {
        return items;
    }

    public void setItems(List<BillModel> items) {
        this.items = items;
    }
}
//"date": "sample string 1",
//        "total": 2,
