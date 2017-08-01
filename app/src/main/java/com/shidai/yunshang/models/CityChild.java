package com.shidai.yunshang.models;

import java.util.List;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/2 14:04
 **/

public class CityChild {
    private String first_letter;
    private String full_name;
    private int id;
    private String name;
    private String region_name;
    private List<CountyMode> children;

    public CityChild(String first_letter, String full_name, int id, String name, String region_name) {
        this.first_letter = first_letter;
        this.full_name = full_name;
        this.id = id;
        this.name = name;
        this.region_name = region_name;
    }

    public List<CountyMode> getChildren() {
        return children;
    }

    public void setChildren(List<CountyMode> children) {
        this.children = children;
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

//children=();"first_letter"="<null>";"full_name"="\U868c\U57e0\U5e02";id=340300;name="\U868c\U57e0\U5e02";"region_name"="\U5b89\U5fbd \U868c\U57e0\U5e02";
