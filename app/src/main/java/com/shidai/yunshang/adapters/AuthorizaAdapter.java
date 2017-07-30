package com.shidai.yunshang.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.shidai.yunshang.adapters.base.BaseRecyclerAdapter;
import com.shidai.yunshang.adapters.viewholders.AuthorViewHold;
import com.shidai.yunshang.adapters.viewholders.AuthorViewHold_;
import com.shidai.yunshang.intefaces.AdapterListener;
import com.shidai.yunshang.networks.responses.ShowupResponse;

/**
 * 创建时间： 2017/7/28.
 * 作者：黄如辉
 * 功能描述：
 */

public class AuthorizaAdapter extends BaseRecyclerAdapter<ShowupResponse, AuthorViewHold> {

    private AdapterListener adapterListener;

    public AuthorizaAdapter(AdapterListener adapterListener) {
        super();
        this.adapterListener = adapterListener;
    }

    @Override
    protected AuthorViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return AuthorViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(AuthorViewHold itemView, final ShowupResponse showupResponse, final int position) {
        itemView.bind(showupResponse);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.setItemClickListener(showupResponse, position);
            }
        });
    }
}
