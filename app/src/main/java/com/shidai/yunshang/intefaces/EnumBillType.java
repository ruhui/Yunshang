package com.shidai.yunshang.intefaces;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/9 10:52
 **/
public enum  EnumBillType {
    //账单类型：1收款，2订单收入，3退款，4提现，5分润、6手续费，7支付
    // 其中：4提现，5分润，6手续费相对于平台是对应第三方费率，企业分润，用户提现

    TYPE_SK, TYPE_DDSR, TYPE_TK, TYPE_TX, TYPE_FR, TYPE_SXF, TYPE_ZF
}
