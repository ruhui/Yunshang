package com.shidai.yunshang.managers;

import com.shidai.yunshang.networks.ApiClient;

/**
 * 创建时间： 2017/8/5.
 * 作者：黄如辉
 * 功能描述：html链接地址管理
 */

public class UrlAddressManger {
    /*分享中二维码*/
    public static final String SHAREQRCODE = ApiClient.BASE_URL_TEST + "qrcode_share";
    /*分享中邀请注册*/
    public static final String SHAREREGISTER = ApiClient.BASE_URL_TEST + "register";
    /*分享中营销页*/
    public static final String SHAREMARKETER = ApiClient.BASE_URL_TEST + "marketing";
    /*注册协议*/
    public static final String REGISTPROTOCOL = ApiClient.BASE_URL_TEST + "reg_protocol";
    /*无忌服饰*/
    public static final String HOMECLOTHADDRESS = ApiClient.BASE_URL_TEST + "dress";
    /*证券交易*/
    public static final String HOMEDEALTRADE = ApiClient.BASE_URL_TEST + "deal_trade";
    /*客服*/
    public static final String CUSTOMSERVICE = ApiClient.BASE_URL_TEST + "custom_service";
    /*主页下面的视频推广*/
    public static final String HOMEVIEWHELP = ApiClient.BASE_URL_TEST + "video_help";
    /*企业微店*/
    public static final String HOMEVSHOPADDRESS = ApiClient.BASE_URL_TEST + "vshop_ad";
    /*珠宝*/
    public static final String HOMEJEWELLERYADDRESS = ApiClient.BASE_URL_TEST + "jewellery_ad";
    /*升级中我要代理*/
    public static final String GOPROXY = ApiClient.BASE_URL_TEST + "proxy";
}
