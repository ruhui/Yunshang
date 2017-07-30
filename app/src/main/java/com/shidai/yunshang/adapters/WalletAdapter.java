package com.shidai.yunshang.adapters;

import android.view.ViewGroup;

import com.shidai.yunshang.adapters.base.BaseRecyclerAdapter;
import com.shidai.yunshang.adapters.viewholders.WalletViewHold;
import com.shidai.yunshang.adapters.viewholders.WalletViewHold_;
import com.shidai.yunshang.models.BillbagModel;
import com.shidai.yunshang.models.ChannelModel;

/**
 * 创建时间： 2017/7/28.
 * 作者：黄如辉
 * 功能描述：
 */

public class WalletAdapter extends BaseRecyclerAdapter<ChannelModel, WalletViewHold> {

    private String payType;

    public void setType(String payType){
        this.payType = payType;
    }

    @Override
    protected WalletViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return WalletViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(WalletViewHold itemView, ChannelModel model, int position) {
        itemView.bind(payType, model);
    }
}
