package com.shidai.yunshang.adapters;

import android.view.ViewGroup;

import com.shidai.yunshang.adapters.base.BaseRecyclerAdapter;
import com.shidai.yunshang.adapters.viewholders.MineMsgViewHold;
import com.shidai.yunshang.adapters.viewholders.MineMsgViewHold_;
import com.shidai.yunshang.adapters.viewholders.SelectBankViewHold;
import com.shidai.yunshang.adapters.viewholders.SelectBankViewHold_;
import com.shidai.yunshang.models.SystemModel;

/**
 * 创建时间： 2017/7/23.
 * 作者：黄如辉
 * 功能描述：
 */

public class MineMsgAdapter extends BaseRecyclerAdapter<SystemModel, MineMsgViewHold> {
    @Override
    protected MineMsgViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return MineMsgViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(MineMsgViewHold itemView, SystemModel model, int position) {
        itemView.bind(model);
    }
}
