package com.shidai.yunshang.networks.responses;

/**
 * 创建时间： 2017/8/8.
 * 作者：黄如辉
 * 功能描述：
 */

public class MerchantDetailResponse {
    private String recommender;
    private String create_time;
    private boolean is_receipt;
    private double mouth_profit;
    private double mouth_proxy;
    private int childs;
    private int id;
    private int auth_status;
    private String mobile;
    private String name;
    private String photo;
    private String auth_status_name;
    private String grade_name;

    public MerchantDetailResponse(String recommender, String create_time, boolean is_receipt, double mouth_profit, double mouth_proxy, int childs, int id, int auth_status, String mobile, String name, String photo, String auth_status_name, String grade_name) {
        this.recommender = recommender;
        this.create_time = create_time;
        this.is_receipt = is_receipt;
        this.mouth_profit = mouth_profit;
        this.mouth_proxy = mouth_proxy;
        this.childs = childs;
        this.id = id;
        this.auth_status = auth_status;
        this.mobile = mobile;
        this.name = name;
        this.photo = photo;
        this.auth_status_name = auth_status_name;
        this.grade_name = grade_name;
    }

    public String getRecommender() {
        return recommender;
    }

    public void setRecommender(String recommender) {
        this.recommender = recommender;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public boolean is_receipt() {
        return is_receipt;
    }

    public void setIs_receipt(boolean is_receipt) {
        this.is_receipt = is_receipt;
    }

    public double getMouth_profit() {
        return mouth_profit;
    }

    public void setMouth_profit(double mouth_profit) {
        this.mouth_profit = mouth_profit;
    }

    public double getMouth_proxy() {
        return mouth_proxy;
    }

    public void setMouth_proxy(double mouth_proxy) {
        this.mouth_proxy = mouth_proxy;
    }

    public int getChilds() {
        return childs;
    }

    public void setChilds(int childs) {
        this.childs = childs;
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
//"recommender": "sample string 2",
//        "create_time": "2017-08-08 00:35:06",
//        "is_receipt": true,
//        "mouth_profit": 5.0,
//        "mouth_proxy": 6.0,
//        "childs": 7,
//        "id": 8,
//        "auth_status": 0,
//        "mobile": "sample string 9",
//        "name": "sample string 10",
//        "photo": "sample string 11",
//        "auth_status_name": "未认证",
//        "grade_name": "sample string 12"