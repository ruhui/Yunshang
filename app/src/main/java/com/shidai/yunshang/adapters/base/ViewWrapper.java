package com.shidai.yunshang.adapters.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/3/9.
 */

public class ViewWrapper<V extends View> extends RecyclerView.ViewHolder {

    private V view;
    public ViewWrapper(V itemView) {
        super(itemView);
        view = itemView;
    }

    public V getView(){
        return view;
    }
}
