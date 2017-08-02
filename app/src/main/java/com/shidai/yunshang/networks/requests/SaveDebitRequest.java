package com.shidai.yunshang.networks.requests;

import com.shidai.yunshang.utils.Tool;

import java.util.Date;

/**
 * 创建时间： 2017/8/2.
 * 作者：黄如辉
 * 功能描述：
 */

public class SaveDebitRequest {
    public String code;
    public String bank_id;//银行id
    public String account_no;//银行卡号
    public String bank_code;//银行卡编号
    public String card_type = "DC";//卡类型 借记（储蓄卡）：DC；贷记（信用卡）：CC
    public String province_id;//省
    public String city_id;//市
    public String area_id;//区
    public String region_name;//区域名称
    public String mobile;//预留电话
    public String bank_name;//所属银行
    public String branch_name;//支行名称
    public String create_time = Tool.formatSimpleDate2(new Date());

    public SaveDebitRequest(String code, String bank_id, String account_no, String bank_code, String province_id, String city_id, String area_id, String region_name, String mobile, String bank_name, String branch_name) {
        this.code = code;
        this.bank_id = bank_id;
        this.account_no = account_no;
        this.bank_code = bank_code;
        this.province_id = province_id;
        this.city_id = city_id;
        this.area_id = area_id;
        this.region_name = region_name;
        this.mobile = mobile;
        this.bank_name = bank_name;
        this.branch_name = branch_name;
    }
}
