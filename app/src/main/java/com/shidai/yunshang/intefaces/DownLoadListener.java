package com.shidai.yunshang.intefaces;

/**
 * 创建时间： 2017/7/3.
 * 作者：黄如辉
 * 功能描述：
 */

public interface DownLoadListener {
    public void finishNotify();
    public void shownotifi();
    public void installApk();
    public void refreshView(int length, int mowlength);
    public void dismisNotification();
    public void cancleNotification();
}
