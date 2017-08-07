package com.shidai.yunshang.models;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/7 20:39
 **/
public class MechantModel {
    private boolean is_receipt;
    private double profit;
    private int id;
    private int auth_status;
    private String mobile;
    private String name;
    private String photo;
    private String auth_status_name;
    private String grade_name;

    public MechantModel(boolean is_receipt, double profit, int id, int auth_status, String mobile, String name, String photo, String auth_status_name, String grade_name) {
        this.is_receipt = is_receipt;
        this.profit = profit;
        this.id = id;
        this.auth_status = auth_status;
        this.mobile = mobile;
        this.name = name;
        this.photo = photo;
        this.auth_status_name = auth_status_name;
        this.grade_name = grade_name;
    }

    public boolean is_receipt() {
        return is_receipt;
    }

    public void setIs_receipt(boolean is_receipt) {
        this.is_receipt = is_receipt;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuth_status() {
        return auth_status;
    }

    public void setAuth_status(int auth_status) {
        this.auth_status = auth_status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAuth_status_name() {
        return auth_status_name;
    }

    public void setAuth_status_name(String auth_status_name) {
        this.auth_status_name = auth_status_name;
    }

    public String getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }
}
//"is_receipt": true,
//        "profit": 3,
//        "id": 4,
//        "auth_status": 0,
//        "mobile": "sample string 5",
//        "name": "sample string 6",
//        "photo": "sample string 7",
//        "auth_status_name": "未认证",
//        "grade_name": "sample string 8"
