package com.shidai.yunshang.view.widget.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shidai.yunshang.R;

public class LoadMoreDefaultFooterView extends RelativeLayout implements LoadMoreUIHandler {

    private TextView mTextView;

    public LoadMoreDefaultFooterView(Context context) {
        this(context, null);
    }

    public LoadMoreDefaultFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreDefaultFooterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupViews();
    }

    private void setupViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.views_load_more_default_footer, this);
        mTextView = (TextView) findViewById(R.id.cube_views_load_more_default_footer_text_view);
        mTextView.setTextColor(getResources().getColor(R.color.color_C8C7C7));
    }

    @Override
    public void onLoading(LoadMoreContainer container) {
        setVisibility(VISIBLE);
        mTextView.setText(R.string.views_load_more_loading);
    }

    @Override
    public void onLoadFinish(LoadMoreContainer container) {
//        if (!hasMore) {
            setVisibility(VISIBLE);
//            if (empty) {
//                mTextView.setText(R.string.views_load_more_loaded_empty);
//            } else {
                mTextView.setText(R.string.views_load_more_loaded_no_more);
//            }
//        } else {
//            setVisibility(INVISIBLE);
//        }
    }

    @Override
    public void onWaitToLoadMore(LoadMoreContainer container) {
        setVisibility(VISIBLE);
        mTextView.setText(R.string.views_load_more_click_to_load_more);
    }

    @Override
    public void onLoadError(LoadMoreContainer container, int errorCode, String errorMessage) {
        mTextView.setText(R.string.views_load_more_error);
    }
}
