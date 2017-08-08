package com.shidai.yunshang.intefaces;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/7/26 14:21
 **/
public interface SelectBankListener<T> {
    public void addBankListener(int bankType);
    public void setOnclickListener(T model, int position);
}
