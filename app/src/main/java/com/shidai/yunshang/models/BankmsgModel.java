package com.shidai.yunshang.models;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/7/26 10:14
 **/
public class BankmsgModel {
    private int id;
    private int merchant_id;
    private String card_type;
    private String account_no;
    private String bank_code;
    private String bank_name;
    private String region_name;
    private int province_id;
    private int city_id;
    private int area_id;
    private String mobile;
    private String brand_name;
    private String account_name;
    private String id_card;

    public BankmsgModel(int id, int merchant_id, String card_type, String account_no, String bank_code, String bank_name, String region_name, int province_id, int city_id, int area_id, String mobile, String brand_name, String account_name, String id_card) {
        this.id = id;
        this.merchant_id = merchant_id;
        this.card_type = card_type;
        this.account_no = account_no;
        this.bank_code = bank_code;
        this.bank_name = bank_name;
        this.region_name = region_name;
        this.province_id = province_id;
        this.city_id = city_id;
        this.area_id = area_id;
        this.mobile = mobile;
        this.brand_name = brand_name;
        this.account_name = account_name;
        this.id_card = id_card;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }
}
//"id": 1,
//        "merchant_id": 2,
//        "card_type": "sample string 3",
//        "account_no": "sample string 4",
//        "bank_code": "sample string 5",
//        "bank_name": "sample string 6",
//        "region_name": "sample string 7",
//        "province_id": 8,
//        "city_id": 9,
//        "area_id": 10,
//        "mobile": "sample string 11",
//        "brand_name": "sample string 12",
//        "account_name": "sample string 13",
//        "id_card": "sample string 14"
