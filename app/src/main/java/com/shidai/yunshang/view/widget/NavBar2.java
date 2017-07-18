package com.shidai.yunshang.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shidai.yunshang.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;


@EViewGroup(R.layout.view_nav_bar2)
public class NavBar2 extends Toolbar {

    @ViewById
    ImageView ivMenuLeft;
    @ViewById
    TextView ivMenuRightTxt;

    @ViewById
    View root;

    @ViewById(R.id.tv_title)
    TextView mTitleTextView;


    private int menuLeftIconRes;

    private int menuRightIconRes;

    private int titleRes;

    private OnMenuClickListener mOnMenuClickListener;

    public NavBar2(Context context) {
        super(context);
    }

    public NavBar2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NavBar);
        menuLeftIconRes = ta.getResourceId(R.styleable.NavBar_menu_icon_left, -1);
        menuRightIconRes = ta.getResourceId(R.styleable.NavBar_menu_icon_right, -1);
        titleRes = ta.getResourceId(R.styleable.NavBar_title_res, -1);
        ta.recycle();
    }


    public void setOnMenuClickListener(OnMenuClickListener listener) {
        mOnMenuClickListener = listener;
    }

    @Click(R.id.ivMenuLeft)
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

    @AfterViews
    void init() {
        setLeftMenuIcon(menuLeftIconRes);
        setTitle(titleRes);
    }


    public void setLeftMenuIcon(@DrawableRes int drawableRes) {
        if (drawableRes == -1) {
            return;
        }
        ivMenuLeft.setImageResource(drawableRes);
        setDisplayLeftMenu(true);
    }



    public void setRightTxt(String str) {
        ivMenuRightTxt.setText(str);
        ivMenuRightTxt.setTextColor(getResources().getColor(R.color.color_333333));
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
