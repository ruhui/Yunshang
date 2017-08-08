package com.shidai.yunshang.intefaces;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/7/18 20:29
 **/
public class RefreshListener {
    public boolean refresh;
    public String tag;

    public RefreshListener(boolean refresh) {
        this.refresh = refresh;
    }

    public RefreshListener(boolean refresh, String tag) {
        this.refresh = refresh;
        this.tag =tag;
    }
}
