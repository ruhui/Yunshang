package com.shidai.yunshang.networks.responses;

/**
 * 创建时间： 2017/8/1.
 * 作者：黄如辉
 * 功能描述：
 */

public class BankCodeAndNameResponse {
    private String bank_code;
    private String bank_name;

    public BankCodeAndNameResponse(String bank_code, String bank_name) {
        this.bank_code = bank_code;
        this.bank_name = bank_name;
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
}
// "bank_code": "sample string 1",
//         "bank_name": "sample string 2"