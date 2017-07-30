package com.shidai.yunshang.view.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shidai.yunshang.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;


@EViewGroup(R.layout.view_nav_bar_wallet)
public class NavBarWallet extends Toolbar {

    @ViewById(R.id.tv_title)
    TextView mTitleTextView;

    @ViewById(R.id.ivMenuLeft)
    ImageView ivMenuLeft;
    @ViewById(R.id.txtLeftView)
    TextView txtLeftView;

    @ViewById(R.id.ivMenuRight)
    ImageView ivMenuRight;
    @ViewById(R.id.txtRightView)
    TextView txtRightView;

    private OnMenuClickListener mOnMenuClickListener;

    public NavBarWallet(Context context) {
        super(context);
    }

    public NavBarWallet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnMenuClickListener(OnMenuClickListener listener) {
        mOnMenuClickListener = listener;
    }

    /*左边菜单*/
    @Click(R.id.relaLeft)
    void onLeftMenuClick(View view) {
        if (mOnMenuClickListener != null) {
            mOnMenuClickListener.onLeftMenuClick(view);
        }

    }

    /*右边菜单*/
    @Click(R.id.relaRight)
    void onRightMenuClick(View view) {
        if (mOnMenuClickListener != null) {
            mOnMenuClickListener.onRightMenuClick(view);
        }
    }

    public void setRightVisable(boolean isvisiable){
        if (isvisiable){
            ivMenuRight.setVisibility(VISIBLE);
        }else{
            ivMenuRight.setVisibility(INVISIBLE);
        }
    }

    public void setIvMenuRight(int menuRight){
        ivMenuRight.setImageResource(menuRight);
    }

    public void setTxtRightView(String rightTxt){
        txtRightView.setText(rightTxt);
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

    public void setMiddleText(@StringRes int titleRes){
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
