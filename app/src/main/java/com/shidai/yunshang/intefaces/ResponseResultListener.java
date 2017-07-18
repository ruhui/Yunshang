package com.shidai.yunshang.intefaces;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/18 16:17
 **/

public interface ResponseResultListener<T>{
    public void success(T returnMsg);
    public void fialed(String resCode, String message);
}