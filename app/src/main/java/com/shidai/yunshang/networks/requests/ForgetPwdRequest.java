package com.shidai.yunshang.networks.requests;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/10 10:47
 **/
public class ForgetPwdRequest {
    private String mobile;
    private String password;
    private String re_password;
    private String code;

    public ForgetPwdRequest(String mobile, String password, String re_password, String code) {
        this.mobile = mobile;
        this.password = password;
        this.re_password = re_password;
        this.code = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRe_password() {
        return re_password;
    }

    public void setRe_password(String re_password) {
        this.re_password = re_password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
