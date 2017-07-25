package com.shidai.yunshang.models;

/**
 * 创建时间： 2017/7/25.
 * 作者：黄如辉
 * 功能描述：
 */

public class SystemModel {
    private int type;
    private int object_id;
    private int id;
    private String title;
    private String content;
    private String create_time;
    private boolean is_read;

    public SystemModel(int type, int object_id, int id, String title, String content, String create_time, boolean is_read) {
        this.type = type;
        this.object_id = object_id;
        this.id = id;
        this.title = title;
        this.content = content;
        this.create_time = create_time;
        this.is_read = is_read;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getObject_id() {
        return object_id;
    }

    public void setObject_id(int object_id) {
        this.object_id = object_id;
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
// "type": 0,
//         "object_id": 1,
//         "id": 1,
//         "title": "sample string 2",
//         "content": "sample string 3",
//         "create_time": "2017-07-25 22:15:50",
//         "is_read": true
