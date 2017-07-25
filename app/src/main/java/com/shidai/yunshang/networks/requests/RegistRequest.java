package com.shidai.yunshang.networks.requests;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/7/25 16:07
 **/
public class RegistRequest {
    public String mobile;
    public String password;
    public String re_password;
    public String code;
    public String recommender;

    public RegistRequest(String mobile, String password, String re_password, String code, String recommender) {
        this.mobile = mobile;
        this.password = password;
        this.re_password = re_password;
        this.code = code;
        this.recommender = recommender;
    }
}
//"mobile": "sample string 1",
//        "password": "sample string 2",
//        "re_password": "sample string 3",
//        "code": "sample string 4",
//        "recommender": "sample string 5"