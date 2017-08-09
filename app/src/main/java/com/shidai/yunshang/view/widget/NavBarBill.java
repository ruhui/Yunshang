package com.shidai.yunshang.view.widget;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.shidai.yunshang.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 账单菜单
 */
@EViewGroup(R.layout.view_nav_bar_bill)
public class NavBarBill extends Toolbar {

    @ViewById(R.id.view)
    TextView view;
    @ViewById(R.id.view1)
    TextView view1;
    @ViewById(R.id.view2)
    TextView view2;

    /*切换到哪个标签*/
    private int switchBlank = 1;

    private OnMenuClickListener mOnMenuClickListener;

    public NavBarBill(Context context) {
        super(context);
    }

    public NavBarBill(Context context, AttributeSet attrs) {
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

    /*收款*/
    @Click(R.id.relaShoukuan)
    void onShoukuanMenuClick(View view) {
        if (mOnMenuClickListener != null) {
            switchBlank = 1;
            mOnMenuClickListener.onSKMenuClick(view);
            switTab();
        }
    }

    /*分润*/
    @Click(R.id.relaBlankCard)
    void onFenrunMenuClick(View view) {
        if (mOnMenuClickListener != null) {
            switchBlank = 2;
            mOnMenuClickListener.onFRMenuClick(view);
            switTab();
        }
    }

    /*提现*/
    @Click(R.id.relaTixian)
    void onTixianMenuClick(View view) {
        if (mOnMenuClickListener != null) {
            switchBlank = 3;
            mOnMenuClickListener.onTXMenuClick(view);
            switTab();
        }
    }

    /*切换标签*/
    public void switTab(){
        if (switchBlank == 1){
            view.setVisibility(VISIBLE);
            view1.setVisibility(INVISIBLE);
            view2.setVisibility(INVISIBLE);
        }else if (switchBlank == 2){
            view.setVisibility(INVISIBLE);
            view1.setVisibility(VISIBLE);
            view2.setVisibility(INVISIBLE);
        }else if (switchBlank == 3){
            view.setVisibility(INVISIBLE);
            view1.setVisibility(INVISIBLE);
            view2.setVisibility(VISIBLE);
        }

    }


    public static abstract class OnMenuClickListener {
        public void onBackClick(View view){};

        public void onSKMenuClick(View view) {
        }

        public void onFRMenuClick(View view) {
        }

        public void onTXMenuClick(View view) {
        }
    }

}
