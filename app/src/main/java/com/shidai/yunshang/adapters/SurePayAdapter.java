package com.shidai.yunshang.adapters;

import android.view.ViewGroup;

import com.shidai.yunshang.adapters.base.BaseRecyclerAdapter;
import com.shidai.yunshang.adapters.viewholders.SureOrderViewHold;
import com.shidai.yunshang.adapters.viewholders.SureOrderViewHold_;
import com.shidai.yunshang.models.SuerPayModel;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/7/21 11:24
 **/
public class SurePayAdapter extends BaseRecyclerAdapter<SuerPayModel, SureOrderViewHold>{

    @Override
    protected SureOrderViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return SureOrderViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(SureOrderViewHold itemView, SuerPayModel model, int position) {
        itemView.bind(model);
    }
}
