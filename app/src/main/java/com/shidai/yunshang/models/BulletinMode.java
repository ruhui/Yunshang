package com.shidai.yunshang.models;

/**
 * 创建时间： 2017/8/5.
 * 作者：黄如辉
 * 功能描述：
 */

public class BulletinMode {
    private int id;
    private String title;
    private String content;
    private String create_time;
    private boolean is_read;

    public BulletinMode(int id, String title, String content, String create_time, boolean is_read) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.create_time = create_time;
        this.is_read = is_read;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public boolean is_read() {
        return is_read;
    }

    public void setIs_read(boolean is_read) {
        this.is_read = is_read;
    }
}
// "id": 1,
//         "title": "sample string 2",
//         "content": "sample string 3",
//         "create_time": "2017-08-05 11:09:10",
//         "is_read": true
