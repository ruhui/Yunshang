package com.shidai.yunshang.models;

/**
 * 创建时间： 2017/6/10.
 * 作者：黄如辉
 * 功能描述：
 */

public class StartAndEndYear {
    private String startYear;
    private String endYear;

    public StartAndEndYear(String startYear, String endYear) {
        this.startYear = startYear;
        this.endYear = endYear;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }
}
