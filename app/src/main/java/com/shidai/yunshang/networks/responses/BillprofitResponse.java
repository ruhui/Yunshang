package com.shidai.yunshang.networks.responses;

import com.shidai.yunshang.models.GradesModel;

import java.util.List;

/**
 * 创建时间： 2017/7/29.
 * 作者：黄如辉
 * 功能描述：
 */

public class BillprofitResponse {
    private double profit;
    private int day_trade_count;
    private double day_trade_amt;
    private double month_profit;
    private int day_user_count;
    private int sort;
    private List<GradesModel> grades;

    public BillprofitResponse(double profit, int day_trade_count, double day_trade_amt, double month_profit, int day_user_count, int sort, List<GradesModel> grades) {
        this.profit = profit;
        this.day_trade_count = day_trade_count;
        this.day_trade_amt = day_trade_amt;
        this.month_profit = month_profit;
        this.day_user_count = day_user_count;
        this.sort = sort;
        this.grades = grades;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public int getDay_trade_count() {
        return day_trade_count;
    }

    public void setDay_trade_count(int day_trade_count) {
        this.day_trade_count = day_trade_count;
    }

    public double getDay_trade_amt() {
        return day_trade_amt;
    }

    public void setDay_trade_amt(double day_trade_amt) {
        this.day_trade_amt = day_trade_amt;
    }

    public double getMonth_profit() {
        return month_profit;
    }

    public void setMonth_profit(double month_profit) {
        this.month_profit = month_profit;
    }

    public int getDay_user_count() {
        return day_user_count;
    }

    public void setDay_user_count(int day_user_count) {
        this.day_user_count = day_user_count;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<GradesModel> getGrades() {
        return grades;
    }

    public void setGrades(List<GradesModel> grades) {
        this.grades = grades;
    }
}
//"profit": 1.0,
//        "day_trade_count": 2,
//        "day_trade_amt": 3.0,
//        "month_profit": 4.0,
//        "day_user_count": 5,
//        "sort": 6,
//        "grades":
