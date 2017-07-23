package com.shidai.yunshang.intefaces;

/**
 * 创建时间： 2017/7/22.
 * 作者：黄如辉
 * 功能描述：
 */

public interface AdapterListener<T> {
    public void setItemClickListener(T t, int position);

}
