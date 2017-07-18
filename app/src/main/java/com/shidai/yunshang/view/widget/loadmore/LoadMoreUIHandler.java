package com.shidai.yunshang.view.widget.loadmore;

public interface LoadMoreUIHandler {

    public void onLoading(LoadMoreContainer container);

    public void onLoadFinish(LoadMoreContainer container);

    public void onWaitToLoadMore(LoadMoreContainer container);

    public void onLoadError(LoadMoreContainer container, int errorCode, String errorMessage);
}