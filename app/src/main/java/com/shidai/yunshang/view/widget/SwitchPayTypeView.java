package com.shidai.yunshang.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.shidai.yunshang.R;
import com.shidai.yunshang.models.BillbagModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import retrofit2.http.POST;

/**
 * 创建时间： 2017/7/28.
 * 作者：黄如辉
 * 功能描述：
 */
@EViewGroup(R.layout.view_swichpaytype)
public class SwitchPayTypeView extends LinearLayout {

    @ViewById(R.id.relaYLPay)
    RelativeLayout relaYLPay;
    @ViewById(R.id.line1)
    View line1;
    @ViewById(R.id.relaZFBPay)
    RelativeLayout relaZFBPay;
    @ViewById(R.id.line2)
    View line2;
    @ViewById(R.id.relaWXPay)
    RelativeLayout relaWXPay;
    @ViewById(R.id.line3)
    View line3;


    public SwitchPayTypeView(Context context) {
        super(context);
    }

    public SwitchPayTypeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /*银联支付*/
    public void setYLPayLisener(OnClickListener clickListener){
        relaYLPay.setOnClickListener(clickListener);
    }

    /*支付宝支付*/
    public void setZFBPayLisener(OnClickListener clickListener){
        relaZFBPay.setOnClickListener(clickListener);
    }

    /*微信支付*/
    public void setWXPayLisener(OnClickListener clickListener){
        relaWXPay.setOnClickListener(clickListener);
    }

    public void showYLline(){
        line1.setVisibility(VISIBLE);
        line2.setVisibility(INVISIBLE);
        line3.setVisibility(INVISIBLE);
    }


    public void showZFBline(){
        line1.setVisibility(INVISIBLE);
        line2.setVisibility(VISIBLE);
        line3.setVisibility(INVISIBLE);
    }

    public void showWXline(){
        line1.setVisibility(INVISIBLE);
        line2.setVisibility(INVISIBLE);
        line3.setVisibility(VISIBLE);
    }


}
