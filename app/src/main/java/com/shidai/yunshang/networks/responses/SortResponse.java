package com.shidai.yunshang.networks.responses;

import java.util.List;

/**
 * 创建时间： 2017/7/29.
 * 作者：黄如辉
 * 功能描述：
 */

public class SortResponse {
    private String photo;
    private String name;
    private double profit;

    public SortResponse(String photo, String name, double profit) {
        this.photo = photo;
        this.name = name;
        this.profit = profit;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }
}

//      "photo": "sample string 2",
//              "name": "sample string 3",
//              "profit": 4.0