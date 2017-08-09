package com.shidai.yunshang.intefaces;

import com.shidai.yunshang.models.BillModel;
import com.shidai.yunshang.models.BillTitleModel;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/9 13:53
 **/
public interface BillAdapterListener {
    public void setOnTitleClickListener(BillTitleModel model, int position);
    public void setOnItemClickListener(BillModel model, int position);
}
