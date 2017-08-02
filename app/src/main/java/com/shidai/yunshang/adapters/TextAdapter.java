package com.shidai.yunshang.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.shidai.yunshang.adapters.base.BaseRecyclerAdapter;
import com.shidai.yunshang.adapters.viewholders.TextViewHold;
import com.shidai.yunshang.adapters.viewholders.TextViewHold_;
import com.shidai.yunshang.intefaces.AdapterListener;
import com.shidai.yunshang.models.BranchbrankModel;

/**
 * 创建时间： 2017/8/2.
 * 作者：黄如辉
 * 功能描述：
 */

public class TextAdapter extends BaseRecyclerAdapter<BranchbrankModel, TextViewHold> {

    private AdapterListener adapterListener;

    public TextAdapter(AdapterListener adapterListener){
        this.adapterListener = adapterListener;
    }

    @Override
    protected TextViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return TextViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(TextViewHold itemView, final BranchbrankModel branchbrankModel, final int position) {
        itemView.bind(branchbrankModel, position);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.setItemClickListener(branchbrankModel, position);
            }
        });
    }
}
