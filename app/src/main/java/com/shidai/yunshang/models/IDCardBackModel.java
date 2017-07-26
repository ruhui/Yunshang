package com.shidai.yunshang.models;

/**
 * 创建时间： 2017/7/26.
 * 作者：黄如辉
 * 功能描述：
 */

public class IDCardBackModel {
    private boolean isrealIDCard;
    private String errorcode;

    public IDCardBackModel(boolean isrealIDCard, String errorcode) {
        this.isrealIDCard = isrealIDCard;
        this.errorcode = errorcode;
    }

    public boolean isrealIDCard() {
        return isrealIDCard;
    }

    public void setIsrealIDCard(boolean isrealIDCard) {
        this.isrealIDCard = isrealIDCard;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }
}
