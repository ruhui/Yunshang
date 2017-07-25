package com.shidai.yunshang.networks.responses;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/7/25 17:33
 **/
public class UsermsgResponse {
    private String qrcode;
    private String recommender;
    private int id;
    private int auth_status;
    private String mobile;
    private String name;
    private String photo;
    private String auth_status_name;
    private String grade_name;
    private int parent_id;

    public UsermsgResponse(String qrcode, String recommender, int id, int auth_status, String mobile, String name, String photo, String auth_status_name, String grade_name, int parent_id) {
        this.qrcode = qrcode;
        this.recommender = recommender;
        this.id = id;
        this.auth_status = auth_status;
        this.mobile = mobile;
        this.name = name;
        this.photo = photo;
        this.auth_status_name = auth_status_name;
        this.grade_name = grade_name;
        this.parent_id = parent_id;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getRecommender() {
        return recommender;
    }

    public void setRecommender(String recommender) {
        this.recommender = recommender;
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

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
}
//"qrcode": "sample string 1",
//        "recommender": "sample string 2",
//        "id": 3,
//        "auth_status": 0,
//        "mobile": "sample string 4",
//        "name": "sample string 5",
//        "photo": "sample string 6",
//        "auth_status_name": "未认证",
//        "grade_name": "sample string 7",
//        "parent_id": 8