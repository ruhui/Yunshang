package com.shidai.yunshang.models;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/7/21 11:20
 **/
public class SuerPayModel {
    private String picture;
    private String title;
    private String content;

    public SuerPayModel(String picture, String title, String content) {
        this.picture = picture;
        this.title = title;
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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
}
