package com.shidai.yunshang.adapters;

import android.view.ViewGroup;

import com.shidai.yunshang.adapters.base.BaseRecyclerAdapter;
import com.shidai.yunshang.adapters.viewholders.BenefitViewHold;
import com.shidai.yunshang.adapters.viewholders.BenefitViewHold_;
import com.shidai.yunshang.models.GradesModel;

/**
 * 创建时间： 2017/7/29.
 * 作者：黄如辉
 * 功能描述：
 */

public class BenefitAdapter extends BaseRecyclerAdapter<GradesModel, BenefitViewHold> {

    @Override
    protected BenefitViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return BenefitViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(BenefitViewHold itemView, GradesModel gradesModel, int position) {
        itemView.bind(gradesModel);
    }
}
