package com.shidai.yunshang.activities;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.activities.base.BaseActivity;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.networks.responses.TransferResponse;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/7 10:45
 **/
@EActivity(R.layout.fragment_successtixian)
public class SuccessTixianActivity extends BaseActivity {
    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;
    /*商户名*/
    @ViewById(R.id.textView50)
    TextView txtCompanyName;
    /*账号*/
    @ViewById(R.id.textView52)
    TextView txtUserPhone;
    /*提现金额*/
    @ViewById(R.id.textView54)
    TextView txtTixianMoney;
    /*提现费*/
    @ViewById(R.id.textView56)
    TextView txtTixianfee;
    /*实际到账*/
    @ViewById(R.id.textView58)
    TextView txtRealMoney;
    /*到账账户*/
    @ViewById(R.id.textView60)
    TextView txtUserCode;
    /*提现时间*/
    @ViewById(R.id.textView62)
    TextView txtTixianTime;

    private TransferResponse tranferRes;


    @AfterViews
    void initView(){
        tranferRes = (TransferResponse) getIntent().getSerializableExtra("tranferRes");
        mNavbar.setMiddleTitle("提现成功");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });
        txtCompanyName.setText(tranferRes.getAccount_name());
        txtUserPhone.setText(tranferRes.getMobile());
        txtTixianMoney.setText("¥"+tranferRes.getTransfer_amount());
        txtTixianfee.setText("¥"+tranferRes.getSettle_amount());
        txtRealMoney.setText("¥"+tranferRes.getAmount());
        txtUserCode.setText(tranferRes.getAccount_no());
        txtTixianTime.setText(tranferRes.getApply_time());
    }

    @Click(R.id.button2)
    void sureButton(){
        finish();
    }

}
