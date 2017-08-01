package com.shidai.yunshang.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 描述：地区
 * 作者：黄如辉  时间 2017/5/13.
 */


public class CountyMode {
    private int parentid;
    private String first_letter;
    private String full_name;
    private int id;
    private String name;
    private String region_name;

    public CountyMode(int parentid, String first_letter, String full_name, int id, String name, String region_name) {
        this.parentid = parentid;
        this.first_letter = first_letter;
        this.full_name = full_name;
        this.id = id;
        this.name = name;
        this.region_name = region_name;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getFirst_letter() {
        return first_letter;
    }

    public void setFirst_letter(String first_letter) {
        this.first_letter = first_letter;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }
}
