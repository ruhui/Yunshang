package com.shidai.yunshang.view.widget.loadmore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.shidai.yunshang.view.widget.pullview.core.EndlessRecyclerOnScrollListener;
import com.shidai.yunshang.view.widget.pullview.core.RecyclerViewUtils;

/**
 * @author wangjie
 */
public class LoadMoreRecyclerViewContainer extends LinearLayout implements LoadMoreContainer {

    private RecyclerView.OnScrollListener mOnScrollListener;
    private LoadMoreUIHandler mLoadMoreUIHandler;
    private LoadMoreHandler mLoadMoreHandler;

    private boolean mIsLoading;
    private boolean mHasMore = true;
    private boolean mAutoLoadMore = true;
    private boolean mLoadError = false;

    private boolean isShow = true;

    private boolean mListEmpty = true;
    private boolean mShowLoadingForFirstPage = false;
    private View mFooterView;

    private RecyclerView mRecyclerView;

    public LoadMoreRecyclerViewContainer(Context context) {
        super(context);
    }

    public LoadMoreRecyclerViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mRecyclerView = (RecyclerView) getChildAt(0);
        init();
    }

    /**
     * @deprecated It's totally wrong. Use {@link #useDefaultFooter} instead.
     */
    @Deprecated
    public void useDefaultHeader() {
        useDefaultFooter();
    }

    public void useDefaultFooter() {
        LoadMoreDefaultFooterView footerView = new LoadMoreDefaultFooterView(getContext());
        footerView.setVisibility(GONE);
        setLoadMoreView(footerView);
        setLoadMoreUIHandler(footerView);
    }

    private void init() {


        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadNextPage(View view) {
                onReachBottom();
            }
        });
    }

    private void tryToPerformLoadMore() {
        if (mIsLoading) {
            return;
        }

//        // no more content and also not load for first page
//        if ( !(mListEmpty && mShowLoadingForFirstPage)) {
//
//            return;
//        }


        mIsLoading = true;

        if (mLoadMoreUIHandler != null) {
            if (mFooterView != null) {

                addFooterView(mFooterView);
            }
            mLoadMoreUIHandler.onLoading(this);
        }
        if (null != mLoadMoreHandler) {
            mLoadMoreHandler.onLoadMore(this);
        }
    }

    public void enableLoadMore(boolean enable) {
        isShow = enable;
    }

    private void onReachBottom() {
        // if has error, just leave what it should be
        if (mLoadError || !isShow || !mHasMore) {
            return;
        }

        if (mAutoLoadMore) {
            tryToPerformLoadMore();
        } else {

            mLoadMoreUIHandler.onWaitToLoadMore(this);

        }
    }

    @Override
    public void setShowLoadingForFirstPage(boolean showLoading) {
        mShowLoadingForFirstPage = showLoading;
    }

    @Override
    public void setAutoLoadMore(boolean autoLoadMore) {
        mAutoLoadMore = autoLoadMore;
    }


    @Override
    public void setLoadMoreView(View view) {
        // has not been initialized
        if (mRecyclerView == null) {
            mFooterView = view;
            return;
        }
        // remove previous
//        if (mFooterView != null && mFooterView != view) {
//            removeFooterView(view);
//        }

        // add current
        mFooterView = view;
//        mFooterView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tryToPerformLoadMore();
//            }
//        });

//        addFooterView(view);
    }

    @Override
    public void setLoadMoreUIHandler(LoadMoreUIHandler handler) {
        mLoadMoreUIHandler = handler;
    }

    @Override
    public void setLoadMoreHandler(LoadMoreHandler handler) {
        mLoadMoreHandler = handler;
    }


    @Override
    public void loadMoreFinish(boolean hasMore) {
        mLoadError = false;
//        mListEmpty = emptyResult;
        mIsLoading = false;
        mHasMore = hasMore;
        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler.onLoadFinish(this);
            if (mFooterView != null) {
                removeFooterView(mFooterView);
                if (!hasMore) {
                    addFooterView(mFooterView);
                }
            }
        }
    }

    @Override
    public void loadMoreError(int errorCode, String errorMessage) {
        mIsLoading = false;
        mLoadError = true;
        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler.onLoadError(this, errorCode, errorMessage);
        }
    }

    protected void addFooterView(View view) {
        RecyclerViewUtils.setFooterView(mRecyclerView, view);
    }

    protected void removeFooterView(View view) {
        RecyclerViewUtils.removeFooterView(mRecyclerView);
    }


}