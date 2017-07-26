package com.shidai.yunshang.models;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/7/26 10:14
 **/
public class BankmsgModel {
    private int id;
    private String card_no;
    private String image_path;
    private String bank_name;

    public BankmsgModel(int id, String card_no, String image_path, String bank_name) {
        this.id = id;
        this.card_no = card_no;
        this.image_path = image_path;
        this.bank_name = bank_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }
}
//"id": 1,
//        "card_no": "sample string 2",
//        "image_path": "sample string 3",
//        "bank_name": "sample string 4"
