package com.shidai.yunshang.adapters;

import android.view.ViewGroup;

import com.shidai.yunshang.adapters.base.BaseRecyclerAdapter;
import com.shidai.yunshang.adapters.viewholders.SortViewHold;
import com.shidai.yunshang.adapters.viewholders.SortViewHold_;
import com.shidai.yunshang.networks.responses.SortResponse;

/**
 * 创建时间： 2017/7/29.
 * 作者：黄如辉
 * 功能描述：
 */

public class SortAdapter extends BaseRecyclerAdapter<SortResponse, SortViewHold> {

    private String cardType;

    public SortAdapter(String cardType){
        this.cardType = cardType;
    }

    @Override
    protected SortViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return SortViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(SortViewHold itemView, SortResponse sortResponse, int position) {
        itemView.bind(sortResponse, position, cardType);
    }

    public void setType(String  cardType) {
        this.cardType = cardType;
    }
}
