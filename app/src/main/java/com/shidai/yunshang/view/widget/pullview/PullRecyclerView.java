package com.shidai.yunshang.view.widget.pullview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.shidai.yunshang.R;
import com.shidai.yunshang.view.widget.loadmore.LoadMoreContainer;
import com.shidai.yunshang.view.widget.loadmore.LoadMoreHandler;
import com.shidai.yunshang.view.widget.loadmore.LoadMoreRecyclerViewContainer;
import com.shidai.yunshang.view.widget.pullview.core.HeaderAndFooterRecyclerViewAdapter;
import com.shidai.yunshang.view.widget.pullview.core.RecyclerViewUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

@EViewGroup(R.layout.view_pull_recyclerview)
public class PullRecyclerView extends LinearLayout {
    public PullRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @ViewById(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @ViewById(R.id.ptr_frame)
    PtrFrameLayout mPtrFrameLayout;

    @ViewById(R.id.load_more_container)
    LoadMoreRecyclerViewContainer mLoadMoreContainer;

    private OnPullListener mListener;

    @AfterViews
    void init() {

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        mPtrFrameLayout.setLoadingMinTime(1000);
        mPtrFrameLayout.disableWhenHorizontalMove(true);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View content, View header) {
//                RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
//                //判断是当前layoutManager是否为LinearLayoutManager
//                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
//                if (layoutManager instanceof LinearLayoutManager) {
//                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
//                    //获取最后一个可见view的位置
//                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
//                    //获取第一个可见view的位置
//                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
//                    return  mRecyclerView.getChildCount() == 0 || mRecyclerView.getChildAt(firstItemPosition).getTop() == 0;
//                }
                return mRecyclerView.getChildCount() == 0 || mRecyclerView.getChildAt(0).getTop() == 0;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                if (mListener != null) {
                    mListener.onRefresh(mRecyclerView);
                }
            }
        });

//        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
//            @Override
//            public void onLoadNextPage(View view) {
//                if (mListener != null) {
//                    mListener.onLoadMore(mRecyclerView);
//                }
//            }
//        });

        mLoadMoreContainer.useDefaultFooter();
        mLoadMoreContainer.setAutoLoadMore(true);
        mLoadMoreContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                mListener.onLoadMore(mRecyclerView);
            }
        });
    }

    public HeaderAndFooterRecyclerViewAdapter getRealAdapter() {
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (adapter == null) {
            return null;
        }
        if (adapter instanceof HeaderAndFooterRecyclerViewAdapter) {
            return (HeaderAndFooterRecyclerViewAdapter) mRecyclerView.getAdapter();
        }
        throw new IllegalStateException("Adapter 必须是HeaderAndFooterRecyclerViewAdapter");
    }


    public RecyclerView.Adapter getInnerAdapter() {
        if (getRealAdapter() != null) {
            return getRealAdapter().getInnerAdapter();
        }
        return null;
    }

    public void notifyDataSetChanged() {
        getRealAdapter().notifyDataSetChanged();
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mRecyclerView.setLayoutManager(manager);
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mRecyclerView.setItemAnimator(animator);
    }


    public void disableWhenHorizontalMove(boolean disable) {
        mPtrFrameLayout.disableWhenHorizontalMove(disable);

    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }


    public void finishLoad(boolean hasMore) {
        mLoadMoreContainer.loadMoreFinish(hasMore);
        mPtrFrameLayout.refreshComplete();
    }


    public void setHeaderView(View headerView) {
        RecyclerViewUtils.setHeaderView(mRecyclerView, headerView);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(new HeaderAndFooterRecyclerViewAdapter(adapter));
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor){
        mRecyclerView.addItemDecoration(decor);
    }

    public PtrFrameLayout getRefreshView() {
        return mPtrFrameLayout;
    }

    public void setRefreshHeader(View header) {
        mPtrFrameLayout.setHeaderView(header);
    }

    public void setOnPullListener(OnPullListener listener) {
        mListener = listener;
    }

    public void setPullToRefresh(boolean pullToRefresh) {
        mPtrFrameLayout.setPullToRefresh(pullToRefresh);
    }

    public void enableRefresh(boolean enable) {
        mPtrFrameLayout.setEnabled(enable);
    }

    public void enableLoadMore(boolean enable) {
        mLoadMoreContainer.enableLoadMore(enable);
    }

    public void autoRefresh() {
        mPtrFrameLayout.autoRefresh();
    }

    public interface OnPullListener {
        void onRefresh(RecyclerView recyclerView);

        void onLoadMore(RecyclerView recyclerView);
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener l){
        mRecyclerView.addOnScrollListener(l);
    }

}
