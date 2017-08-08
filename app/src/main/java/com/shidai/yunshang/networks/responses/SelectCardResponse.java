package com.shidai.yunshang.networks.responses;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/8 14:42
 **/
public class SelectCardResponse {
    private int status;
    private String request_url;
    private String msg;

    public SelectCardResponse(int status, String request_url, String msg) {
        this.status = status;
        this.request_url = request_url;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRequest_url() {
        return request_url;
    }

    public void setRequest_url(String request_url) {
        this.request_url = request_url;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
//"status": 1,
//        "request_url": "sample string 2",
//        "msg": "sample string 3"