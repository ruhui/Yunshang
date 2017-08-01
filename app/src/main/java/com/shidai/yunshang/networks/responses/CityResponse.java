package com.shidai.yunshang.networks.responses;

import com.shidai.yunshang.models.CityChild;

import java.util.List;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/2 10:42
 **/

public class CityResponse {
    private int id;
    private String name;
    private int parent_id;
    private String full_name;
    private String region_name;
    private String first_letter;
    private List<CityChild> children;

    public CityResponse(int id, String name, int parent_id, String full_name, String region_name, String first_letter, List<CityChild> children) {
        this.id = id;
        this.name = name;
        this.parent_id = parent_id;
        this.full_name = full_name;
        this.region_name = region_name;
        this.first_letter = first_letter;
        this.children = children;
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

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getFirst_letter() {
        return first_letter;
    }

    public void setFirst_letter(String first_letter) {
        this.first_letter = first_letter;
    }

    public List<CityChild> getChildren() {
        return children;
    }

    public void setChildren(List<CityChild> children) {
        this.children = children;
    }
}

//"id": 1,
//        "name": "sample string 2",
//        "sort": 3,
//        "parent_id": 4,
//        "depth": 5,
//        "path": "sample string 6",
//        "full_name": "sample string 7",
//        "region_name": "sample string 8",
//        "first_letter": "sample string 9",
//        "children": []

