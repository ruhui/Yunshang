package com.shidai.yunshang.adapters.base;

import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewGroup;

import java.util.IdentityHashMap;

/**
 * Created by Administrator on 2017/3/9.
 */

public abstract class BaseRecyclerAdapter<E, V extends View> extends ArrayRecyclerAdapter<E, ViewWrapper<V> >{

    private OnItemClickListener<E> mItemClickListener;
    private IdentityHashMap<Integer, OnViewClickListener<E>> mViewClickListeners;

    @Override
    public ViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<V>(onCreateItemView(parent, viewType));
    }
    public void setItemClickListener(OnItemClickListener<E> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void addOnViewClickListener(@IdRes int viewId, OnViewClickListener<E> viewClickListener) {
        if (mViewClickListeners == null) {
            mViewClickListeners = new IdentityHashMap<>();
        }
        mViewClickListeners.put(new Integer(viewId), viewClickListener);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<V> holder, final int position) {
        V view = holder.getView();

        if (mItemClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mItemClickListener.onItemClick(v, get(position), position);
                }

            });
        }

        if (mViewClickListeners != null && mViewClickListeners.size() > 0) {
            for (final Integer viewId : mViewClickListeners.keySet()) {
                View v = view.findViewById(viewId);
                if (v == null) {
                    continue;
                }
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewClickListeners.get(viewId).onItemClick(v, get(position), position);
                    }
                });

            }
        }

        onBindView(view, get(position), position);
    }
    protected abstract V onCreateItemView(ViewGroup parent, int viewType);
    protected abstract void onBindView(V itemView, E e, int position);


    public interface OnItemClickListener<T>{
        void onItemClick(View view, T object, int position);
    }

    public interface OnViewClickListener<T>{
        void onItemClick(View view, T object, int position);
    }
}
