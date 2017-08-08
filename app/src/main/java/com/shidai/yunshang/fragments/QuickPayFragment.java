package com.shidai.yunshang.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.RefreshListener;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

/**
 * 描述：首页支付成功界面
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/8 16:50
 **/
@EFragment(R.layout.fragment_quickpay)
public class QuickPayFragment extends BaseFragment {
    @ViewById(R.id.textView78)
    TextView txtMoney;
    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;

    private double payMoney = 0;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        payMoney = getArguments().getDouble("money");
    }

    @AfterViews
    void initView(){
        mNavbar.setMiddleTitle("支付成功");
        txtMoney.setText("¥" + Tool.formatPrice(payMoney));
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });
    }

    @Click(R.id.button2)
    void sumeSumit(){
        finishFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(new RefreshListener(true, "finishFragment"));
    }
}
