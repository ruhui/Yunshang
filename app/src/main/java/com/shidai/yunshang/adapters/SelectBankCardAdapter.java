package com.shidai.yunshang.adapters;

import android.view.ViewGroup;

import com.shidai.yunshang.adapters.base.BaseRecyclerAdapter;
import com.shidai.yunshang.adapters.viewholders.SelectBankViewHold;
import com.shidai.yunshang.adapters.viewholders.SelectBankViewHold_;
import com.shidai.yunshang.models.SelectBankCardModel;

/**
 * 创建时间： 2017/7/22.
 * 作者：黄如辉
 * 功能描述：
 */

public class SelectBankCardAdapter extends BaseRecyclerAdapter<SelectBankCardModel, SelectBankViewHold> {

    @Override
    protected SelectBankViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return SelectBankViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(SelectBankViewHold itemView, SelectBankCardModel model, int position) {
        itemView.bind(model);
    }
}
