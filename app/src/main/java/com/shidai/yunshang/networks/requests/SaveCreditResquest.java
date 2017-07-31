package com.shidai.yunshang.networks.requests;

/**
 * 创建时间： 2017/8/1.
 * 作者：黄如辉
 * 功能描述：添加编辑信用卡
 */

public class SaveCreditResquest {
    public String code;
    public long bank_id;
    public String bank_name;
    public String account_no;
    public String bank_code;
    public String card_type;
    public String account_name;
    public String id_card;
    public String card_cvv;
    public String mobile;
    public String card_expire_time;
    public String create_time;

    public SaveCreditResquest(String code, long bank_id, String bank_name, String account_no, String bank_code, String card_type, String account_name, String id_card, String card_cvv, String mobile, String card_expire_time, String create_time) {
        this.code = code;
        this.bank_id = bank_id;
        this.bank_name = bank_name;
        this.account_no = account_no;
        this.bank_code = bank_code;
        this.card_type = card_type;
        this.account_name = account_name;
        this.id_card = id_card;
        this.card_cvv = card_cvv;
        this.mobile = mobile;
        this.card_expire_time = card_expire_time;
        this.create_time = create_time;
    }


}
// "code": "sample string 2",
//         "bank_id": 3,
//         "bank_name": "sample string 4",
//         "account_no": "sample string 5",
//         "bank_code": "sample string 6",
//         "card_type": "sample string 7",
//         "account_name": "sample string 8",
//         "id_card": "sample string 9",
//         "card_cvv": "sample string 10",
//         "mobile": "sample string 11",
//         "card_expire_time": "sample string 12",
//         "create_time": "2017-08-01 00:10:03"