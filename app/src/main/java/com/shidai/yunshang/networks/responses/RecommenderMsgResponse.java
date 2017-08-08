package com.shidai.yunshang.networks.responses;

/**
 * 描述：推荐人信息
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/8 9:43
 **/
public class RecommenderMsgResponse {
    private String mobile;
    private String name;
    private String photo;
    private String qrcode;

    public RecommenderMsgResponse(String mobile, String name, String photo, String qrcode) {
        this.mobile = mobile;
        this.name = name;
        this.photo = photo;
        this.qrcode = qrcode;
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

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
}
//"mobile": "sample string 1",
//        "name": "sample string 2",
//        "photo": "sample string 3",
//        "qrcode": "sample string 4"