package com.shidai.yunshang.fragments.base;

import android.support.v7.widget.RecyclerView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.view.widget.pullview.PullRecyclerView;
import com.shidai.yunshang.view.widget.pullview.core.HeaderAndFooterRecyclerViewAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment
public abstract class BasePullRecyclerFragment extends BaseFragment {

    @ViewById(R.id.pull_recycler_view)
    protected PullRecyclerView mPullRecyclerView;
    protected static final int PAGE_SIZE = 20;

    @AfterViews
    protected void init() {
        if (mPullRecyclerView == null) {
            throw new IllegalArgumentException("布局文件中缺少id为pull_recycler_view的PullRecyclerView");
        }
        mPullRecyclerView.setOnPullListener(new PullRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView recyclerView) {
                BasePullRecyclerFragment.this.onRefresh(recyclerView);
            }

            @Override
            public void onLoadMore(RecyclerView recyclerView) {
                BasePullRecyclerFragment.this.onLoadMore(recyclerView);
            }
        });
        initView(mPullRecyclerView);
    }

    //    public void enableLoadingMore(boolean enable) {
    //        mPullRecyclerView.enableLoadingMore(enable);
    //    }

    public void enablePullToRefresh(boolean enable) {
        mPullRecyclerView.setPullToRefresh(enable);
    }

    //    protected void beginRefreshing() {
    //        mPullRecyclerView.beginRefreshing();
    //    }
    //
    //    protected void stopRefreshing() {
    //        mPullRecyclerView.endRefreshing();
    //    }
    //
    //
    //    protected void stopLoadingMore() {
    //        mPullRecyclerView.endLoadingMore();
    //    }
    public void finishLoad(boolean hasMore) {
        mPullRecyclerView.finishLoad(hasMore);
    }

    public RecyclerView getRecyclerView() {
        return mPullRecyclerView.getRecyclerView();
    }

    public PullRecyclerView getPullRecyclerView(){
        return mPullRecyclerView;
    }
    //    public void showEmptyLayout(){
    //        mPullRecyclerView.showEmpty();
    //    }

    public HeaderAndFooterRecyclerViewAdapter getRealAdapter() {
        return mPullRecyclerView.getRealAdapter();
    }


    public RecyclerView.Adapter getInnerAdapter() {
        if (getRealAdapter() != null) {
            return getRealAdapter().getInnerAdapter();
        }
        throw new IllegalStateException("还没设置adapter");
    }

    public void notifyDataSetChanged() {
        if (getRealAdapter() != null) {
            getRealAdapter().notifyDataSetChanged();
        }
    }

    public void notifyItemRemoved(int index) {
        if (getRealAdapter() != null) {
            getRealAdapter().notifyItemRemoved(index);
        }
    }

    //    public BGARefreshLayout getRefreshView() {
    //        return mPullRecyclerView.getRefreshView();
    //    }

    //    public LoadMoreListViewContainer getLoadMoreView() {
    //        return mPullRecyclerView.getLoadMoreView();
    //    }

    //    public void setRefreshHeader(View header) {
    //        mPullRecyclerView.setRefreshHeader(header);
    //    }


    protected abstract void initView(PullRecyclerView recyclerView);

    protected abstract void onRefresh(RecyclerView recyclerView);

    protected abstract void onLoadMore(RecyclerView recyclerView);


}
