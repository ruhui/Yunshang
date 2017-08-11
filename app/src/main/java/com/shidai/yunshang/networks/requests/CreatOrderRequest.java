package com.shidai.yunshang.networks.requests;

import com.shidai.yunshang.intefaces.MergePayCode;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/8 10:43
 **/
public class CreatOrderRequest {
    public String amount;
    public String pay_code;

    public CreatOrderRequest(String amount, MergePayCode paycode) {
        this.amount = amount;
        this.pay_code = getpayCode(paycode);
    }

    private String getpayCode( MergePayCode paycode) {
        //ALIPAY_QR, WXPAY_QR, JDPAY_QR, WXPAY_JS, ALIPAY_JS, UNIONPAY, GATEWAY
        String payCode = "";
        switch (paycode){
            case ALIPAY_JS:
                payCode = "ALIPAY_JS";
                break;
            case WXPAY_JS:
                payCode = "WXPAY_JS";
                break;
            case UNIONPAY:
                payCode = "UNIONPAY";
                break;
            case GATEWAY:
                payCode = "GATEWAY";
                break;
        }
        return payCode;
    }
}
//"amount": 2.0,
//        "pay_code": "sample string 3"