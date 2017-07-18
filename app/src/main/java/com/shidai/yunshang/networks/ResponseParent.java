package com.shidai.yunshang.networks;

/**
 * 描述：众众车返回
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/14 16:02
 **/
public class ResponseParent<T> {
    public String result_code;
    public String result_msg;
    public T data;

    public ResponseParent(String result_code, String result_msg, T data) {
        this.result_code = result_code;
        this.result_msg = result_msg;
        this.data = data;
    }
}
