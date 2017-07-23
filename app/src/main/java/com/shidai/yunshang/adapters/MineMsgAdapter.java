package com.shidai.yunshang.adapters;

import android.view.ViewGroup;

import com.shidai.yunshang.adapters.base.BaseRecyclerAdapter;
import com.shidai.yunshang.adapters.viewholders.SelectBankViewHold;
import com.shidai.yunshang.adapters.viewholders.SelectBankViewHold_;

/**
 * 创建时间： 2017/7/23.
 * 作者：黄如辉
 * 功能描述：
 */

public class MineMsgAdapter extends BaseRecyclerAdapter<String, SelectBankViewHold> {
    @Override
    protected SelectBankViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return SelectBankViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(SelectBankViewHold itemView, String s, int position) {

    }
}
