package com.shidai.yunshang.models;

/**
 * 创建时间： 2017/7/29.
 * 作者：黄如辉
 * 功能描述：
 */

public class GradesModel {
    private int grade_id;
    private String name;
    private int count;

    public GradesModel(int grade_id, String name, int count) {
        this.grade_id = grade_id;
        this.name = name;
        this.count = count;
    }

    public int getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(int grade_id) {
        this.grade_id = grade_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
// "grade_id": 1,
//         "name": "sample string 2",
//         "count": 3
