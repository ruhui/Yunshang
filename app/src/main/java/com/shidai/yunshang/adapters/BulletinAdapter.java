package com.shidai.yunshang.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.shidai.yunshang.adapters.base.BaseRecyclerAdapter;
import com.shidai.yunshang.adapters.viewholders.BulletViewHold;
import com.shidai.yunshang.adapters.viewholders.BulletViewHold_;
import com.shidai.yunshang.intefaces.AdapterListener;
import com.shidai.yunshang.models.BulletinMode;

/**
 * 创建时间： 2017/8/5.
 * 作者：黄如辉
 * 功能描述：
 */

public class BulletinAdapter extends BaseRecyclerAdapter<BulletinMode, BulletViewHold> {

    private AdapterListener adapterListener;

    public BulletinAdapter(AdapterListener adapterListener) {
        super();
        this.adapterListener = adapterListener;
    }

    @Override
    protected BulletViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return BulletViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(BulletViewHold itemView, final BulletinMode bulletinMode, final int position) {

        itemView.bind(bulletinMode);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.setItemClickListener(bulletinMode, position);
            }
        });
    }
}
