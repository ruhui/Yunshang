package com.shidai.yunshang.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.shidai.yunshang.adapters.base.BaseRecyclerAdapter;
import com.shidai.yunshang.adapters.viewholders.MechantViewHold;
import com.shidai.yunshang.adapters.viewholders.MechantViewHold_;
import com.shidai.yunshang.intefaces.AdapterListener;
import com.shidai.yunshang.models.MechantModel;

/**
 * 创建时间： 2017/8/7.
 * 作者：黄如辉
 * 功能描述：
 */

public class MechantListAdapter extends BaseRecyclerAdapter<MechantModel, MechantViewHold> {

    private AdapterListener adapterListener;

    public MechantListAdapter(AdapterListener adapterListener){
        this.adapterListener = adapterListener;
    }

    @Override
    protected MechantViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return MechantViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(MechantViewHold itemView, final MechantModel mechantModel, final int position) {
        itemView.bind(mechantModel);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.setItemClickListener(mechantModel, position);
            }
        });
    }
}
