package com.shidai.yunshang.view.widget.loadmore;

import android.view.View;

public interface LoadMoreContainer {

    public void setShowLoadingForFirstPage(boolean showLoading);

    public void setAutoLoadMore(boolean autoLoadMore);

//    public void setOnScrollListener(AbsListView.OnScrollListener l);

    public void setLoadMoreView(View view);

    public void setLoadMoreUIHandler(LoadMoreUIHandler handler);

    public void setLoadMoreHandler(LoadMoreHandler handler);


    public void enableLoadMore(boolean enable);

    public void loadMoreFinish(boolean hasMore);

    /**
     * When something unexpected happened while loading the data
     *
     * @param errorCode
     * @param errorMessage
     */
    public void loadMoreError(int errorCode, String errorMessage);
}
