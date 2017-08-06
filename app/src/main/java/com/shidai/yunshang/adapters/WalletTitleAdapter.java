package com.shidai.yunshang.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.shidai.yunshang.adapters.base.BaseRecyclerAdapter;
import com.shidai.yunshang.adapters.viewholders.WalletTitleViewHold;
import com.shidai.yunshang.adapters.viewholders.WalletTitleViewHold_;
import com.shidai.yunshang.intefaces.AdapterListener;
import com.shidai.yunshang.models.BillbagModel;
import com.shidai.yunshang.view.widget.SwitchPayTypeView;
import com.shidai.yunshang.view.widget.SwitchPayTypeView_;

/**
 * 创建时间： 2017/8/6.
 * 作者：黄如辉
 * 功能描述：
 */

public class WalletTitleAdapter extends BaseRecyclerAdapter<BillbagModel, WalletTitleViewHold> {

    private AdapterListener adapterListener;

    public WalletTitleAdapter( AdapterListener adapterListener){
        this.adapterListener = adapterListener;
    }

    @Override
    protected WalletTitleViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return WalletTitleViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(WalletTitleViewHold itemView, final BillbagModel model, final int position) {
        itemView.bind(model, position);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.setItemClickListener(model, position);
            }
        });
    }
}
