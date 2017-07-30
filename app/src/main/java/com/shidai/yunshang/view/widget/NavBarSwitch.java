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


@EViewGroup(R.layout.view_nav_bar_switch)
public class NavBarSwitch extends Toolbar {

    @ViewById(R.id.view)
    TextView view;
    @ViewById(R.id.view1)
    TextView view1;
    @ViewById(R.id.txtRightView)
    TextView txtRightView;
    @ViewById(R.id.txtLeft)
    TextView txtLeft;

    public void setLeftTitle(String leftTitle){
        txtLeft.setText(leftTitle);
    }

    public void setTxtRightView(String rightView){
        txtRightView.setText(rightView);
    }

    /*是否切换到银行卡*/
    private boolean switchBlank = false;

    private OnMenuClickListener mOnMenuClickListener;

    public NavBarSwitch(Context context) {
        super(context);
    }

    public NavBarSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setOnMenuClickListener(OnMenuClickListener listener) {
        mOnMenuClickListener = listener;
    }

    @Click(R.id.ivMenuLeft)
    void onLeftMenuClick(View view) {
        if (mOnMenuClickListener != null) {
            mOnMenuClickListener.onBackClick(view);
        }

    }

    /*信用卡*/
    @Click(R.id.relaXinyCard)
    void onRightMenuClick(View view) {
        if (mOnMenuClickListener != null) {
            switchBlank = false;
            mOnMenuClickListener.onXyMenuClick(view);
            switTab();
        }
    }

    /*银行卡*/
    @Click(R.id.relaBlankCard)
    void onBankMenuClick(View view) {
        if (mOnMenuClickListener != null) {
            switchBlank = true;
            mOnMenuClickListener.onBankMenuClick(view);
            switTab();
        }

    }

    /*切换标签*/
    public void switTab(){
        if (!switchBlank){
            view.setVisibility(VISIBLE);
            view1.setVisibility(INVISIBLE);
        }else{
            view.setVisibility(INVISIBLE);
            view1.setVisibility(VISIBLE);
        }

    }


    public static abstract class OnMenuClickListener {
        public void onBackClick(View view){};

        public void onXyMenuClick(View view) {
        }

        public void onBankMenuClick(View view) {
        }

    }

}
