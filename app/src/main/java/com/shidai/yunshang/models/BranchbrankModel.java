package com.shidai.yunshang.models;

/**
 * 创建时间： 2017/8/1.
 * 作者：黄如辉
 * 功能描述：
 */

public class BranchbrankModel {
    private int id;
    private String bank_code;
    private int province_id;
    private int city_id;
    private String branch_name;
    private String address;

    public BranchbrankModel(int id, String bank_code, int province_id, int city_id, String branch_name, String address) {
        this.id = id;
        this.bank_code = bank_code;
        this.province_id = province_id;
        this.city_id = city_id;
        this.branch_name = branch_name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
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

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
//"id": 1,
//        "bank_code": "sample string 2",
//        "province_id": 3,
//        "city_id": 4,
//        "branch_name": "sample string 5",
//        "address": "sample string 6"
