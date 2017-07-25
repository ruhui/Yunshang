package com.shidai.yunshang.networks.requests;

import com.shidai.yunshang.intefaces.EnumSendUserType;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/14.
 */

public class SendRegsmsRequest {
    public String mobile;
    public String use_type;

    public SendRegsmsRequest(String mobile, EnumSendUserType usetype) {
        this.mobile = mobile;
        this.use_type = getusertype(usetype);
    }

    String getusertype(EnumSendUserType usetype){
        String userType = "";
        switch (usetype){
            case DEPOSIT:
                //提现方式
                userType = "deposit";
                break;
            case REGISTER:
                //注册
                userType = "register";
                break;
            case FORGET:
                //忘记密码
                userType = "forget";
                break;
            case CHANGEMOBILE:
                //修改密码
                userType = "change_mobile";
                break;
            case REMOVEMOBILE:
                //解除手机绑定
                userType = "remove_mobile";
                break;
            case VERIFIED:
                //认证
                userType = "verified";
                break;
        }
        return userType;
    }
}


//  "mobile": "sample string 1",
//          "use_type": "sample string 2"