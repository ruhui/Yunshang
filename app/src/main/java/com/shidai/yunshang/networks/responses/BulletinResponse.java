package com.shidai.yunshang.networks.responses;

/**
 * 创建时间： 2017/7/25.
 * 作者：黄如辉
 * 功能描述：
 */

public class BulletinResponse {
    private String title;
    private int new_count;

    public BulletinResponse(String title, int new_count) {
        this.title = title;
        this.new_count = new_count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNew_count() {
        return new_count;
    }

    public void setNew_count(int new_count) {
        this.new_count = new_count;
    }
}
//"title": "sample string 1",
//        "new_count": 2