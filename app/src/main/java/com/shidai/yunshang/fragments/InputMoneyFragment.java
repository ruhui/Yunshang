package com.shidai.yunshang.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.NavBar;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：输入金额
 * 创建作者： 黄如辉
 * 创建时间： 2017/7/20 14:26
 **/
@EFragment(R.layout.fragment_inputmoney)
public class InputMoneyFragment extends BaseFragment {

    private String navbarTitle;
    private String moneyDes = "0";

    @ViewById(R.id.mNavbar)
    NavBarBack navBarBack;
    //金额
    @ViewById(R.id.txtMoney)
    TextView txtMoney;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navbarTitle = getArguments().getString("navbarTitle");
    }

    @AfterViews
    void initView(){
        navBarBack.setMiddleTitle(navbarTitle);
        navBarBack.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });

        txtMoney.setText(moneyDes);
    }

    @Click(R.id.btn0)
    void zero(){
        moneyDes = Tool.formatInputPrice(moneyDes, "0");
        txtMoney.setText(moneyDes);
    }

    @Click(R.id.btn1)
    void one(){
        moneyDes = Tool.formatInputPrice(moneyDes, "1");
        txtMoney.setText(moneyDes);
    }

    @Click(R.id.btn2)
    void two(){
        moneyDes = Tool.formatInputPrice(moneyDes, "2");
        txtMoney.setText(moneyDes);
    }

    @Click(R.id.btn3)
    void three(){
        moneyDes = Tool.formatInputPrice(moneyDes, "3");
        txtMoney.setText(moneyDes);
    }

    @Click(R.id.btn4)
    void four(){
        moneyDes = Tool.formatInputPrice(moneyDes, "4");
        txtMoney.setText(moneyDes);
    }

    @Click(R.id.btn5)
    void five(){
        moneyDes = Tool.formatInputPrice(moneyDes, "5");
        txtMoney.setText(moneyDes);
    }

    @Click(R.id.btn6)
    void six(){
        moneyDes = Tool.formatInputPrice(moneyDes, "6");
        txtMoney.setText(moneyDes);
    }

    @Click(R.id.btn7)
    void seven(){
        moneyDes = Tool.formatInputPrice(moneyDes, "7");
        txtMoney.setText(moneyDes);
    }

    @Click(R.id.btn8)
    void eight(){
        moneyDes = Tool.formatInputPrice(moneyDes, "8");
        txtMoney.setText(moneyDes);
    }

    @Click(R.id.btn9)
    void nine(){
        moneyDes = Tool.formatInputPrice(moneyDes, "9");
        txtMoney.setText(moneyDes);
    }

    /*删除*/
    @Click(R.id.btnSc)
    void sc(){
        moneyDes = Tool.formatInputPrice(moneyDes, "sc");
        txtMoney.setText(moneyDes);
    }

    /*清空*/
    @Click(R.id.btnC)
    void btnC(){
        moneyDes = "0";
        txtMoney.setText(moneyDes);
    }

    /*逗号*/
    @Click(R.id.btnDou)
    void btnDou(){
        moneyDes = Tool.formatInputPrice(moneyDes, ".");
        txtMoney.setText(moneyDes);
    }

    /*提交，收款*/
    @Click(R.id.btnSubmit)
    void btnSubmit(){
        SurePayFragment fragment = SurePayFragment_.builder().build();
        Bundle bundle = new Bundle();
        bundle.putString("orderId", "");
        bundle.putString("orderType", "");
        bundle.putString("orderMoney", Tool.formatPrice(moneyDes));
        fragment.setArguments(bundle);
        showFragment(getActivity(), fragment);
    }
}
