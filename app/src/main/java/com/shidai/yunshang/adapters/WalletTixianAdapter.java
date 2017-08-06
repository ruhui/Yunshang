package com.shidai.yunshang.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.shidai.yunshang.adapters.base.BaseRecyclerAdapter;
import com.shidai.yunshang.adapters.viewholders.WalletTixianViewHolde;
import com.shidai.yunshang.adapters.viewholders.WalletTixianViewHolde_;
import com.shidai.yunshang.intefaces.AdapterListener;
import com.shidai.yunshang.networks.responses.SettletypeResponse;

/**
 * 创建时间： 2017/8/7.
 * 作者：黄如辉
 * 功能描述：
 */

public class WalletTixianAdapter extends BaseRecyclerAdapter<SettletypeResponse, WalletTixianViewHolde> {

    private AdapterListener adapterListener;

    public WalletTixianAdapter( AdapterListener adapterListener){
        this.adapterListener = adapterListener;
    }

    @Override
    protected WalletTixianViewHolde onCreateItemView(ViewGroup parent, int viewType) {
        return WalletTixianViewHolde_.build(parent.getContext());
    }

    @Override
    protected void onBindView(WalletTixianViewHolde itemView, final SettletypeResponse settletypeResponse, final int position) {
        itemView.bind(settletypeResponse);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.setItemClickListener(settletypeResponse, position);
            }
        });
    }
}
