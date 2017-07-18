package com.shidai.yunshang.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shidai.yunshang.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;


@EViewGroup(R.layout.view_nav_bar)
public class NavBar extends Toolbar {

    @ViewById(R.id.ivMenuRightTxt)
    TextView ivMenuRightTxt;
    @ViewById(R.id.tv_title)
    TextView mTitleTextView;
    @ViewById(R.id.ivMenuLeft)
    ImageView ivMenuLeft;
    @ViewById(R.id.txtLeftView)
    TextView txtLeftView;

    private OnMenuClickListener mOnMenuClickListener;

    public NavBar(Context context) {
        super(context);
    }

    public NavBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnMenuClickListener(OnMenuClickListener listener) {
        mOnMenuClickListener = listener;
    }

    @Click(R.id.relaLeft)
    void onLeftMenuClick(View view) {
        if (mOnMenuClickListener != null) {
            mOnMenuClickListener.onLeftMenuClick(view);
        }

    }

    @Click(R.id.ivMenuRightTxt)
    void onRightMenuClick(View view) {
        if (mOnMenuClickListener != null) {
            mOnMenuClickListener.onRightMenuClick(view);
        }
    }


    public void setLeftMenuIcon(@DrawableRes int drawableRes) {
        if (drawableRes == -1) {
            return;
        }
        ivMenuLeft.setImageResource(drawableRes);
        setDisplayLeftMenu(true);
    }

    public void setLeftText(String leftText){
        txtLeftView.setText(leftText);
    }



    public void setRightTxt(String str) {
        ivMenuRightTxt.setText(str);
    }

    public void setRightTxtColor(int color){
        ivMenuRightTxt.setTextColor(getResources().getColor(color));
    }

    public void setDisplayLeftMenu(boolean enable) {
        ivMenuLeft.setVisibility(enable ? VISIBLE : GONE);
    }


    public void setTitleEnabled(boolean isShow) {
        mTitleTextView.setVisibility(isShow ? VISIBLE : GONE);
    }


    public void setLeftMenuEnabled(boolean isShow) {
        ivMenuLeft.setVisibility(isShow ? VISIBLE : GONE);
    }


    @Override
    public void setTitle(@StringRes int titleRes) {
        if (titleRes <= 0) {
            return;
        }
        mTitleTextView.setText(titleRes);
    }


    public void setMiddleTitle(CharSequence title) {
        mTitleTextView.setText(title);
    }

    public void setMiddleTextColor(int color){
        mTitleTextView.setTextColor(getResources().getColor(color));
    }

    public void setMiddleTitleColor(int color) {
        mTitleTextView.setTextColor(color);
    }


    public static abstract class OnMenuClickListener {
        public void onLeftMenuClick(View view) {
        }

        public void onRightMenuClick(View view) {
        }

    }

}
