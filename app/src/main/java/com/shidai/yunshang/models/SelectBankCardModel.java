package com.shidai.yunshang.models;

/**
 * 创建时间： 2017/7/22.
 * 作者：黄如辉
 * 功能描述：
 */

public class SelectBankCardModel {
    private String icon;
    private String title;
    private String cardNum;

    public SelectBankCardModel(String icon, String title, String cardNum) {
        this.icon = icon;
        this.title = title;
        this.cardNum = cardNum;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
}
