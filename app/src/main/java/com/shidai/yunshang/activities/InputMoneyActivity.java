package com.shidai.yunshang.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.activities.base.BaseActivity;
import com.shidai.yunshang.fragments.SurePayFragment;
import com.shidai.yunshang.fragments.SurePayFragment_;
import com.shidai.yunshang.intefaces.MergePayCode;
import com.shidai.yunshang.intefaces.RefreshListener;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.CreatOrderResponse;
import com.shidai.yunshang.utils.ToastUtil;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;

import rx.Subscriber;

/**
 * 描述：输入金额
 * 创建作者： 黄如辉
 * 创建时间： 2017/7/20 14:26
 **/
@EActivity(R.layout.fragment_inputmoney)
public class InputMoneyActivity extends BaseActivity {

    private String navbarTitle;
    private String moneyDes = "0", payCode = "";

    @ViewById(R.id.mNavbar)
    NavBarBack navBarBack;
    //金额
    @ViewById(R.id.txtMoney)
    TextView txtMoney;

    @AfterViews
    void initView(){
        navbarTitle = getIntent().getStringExtra("navbarTitle");
        payCode = getIntent().getStringExtra("payCode");
        navBarBack.setMiddleTitle(navbarTitle);
        navBarBack.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
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
        if (Double.valueOf(moneyDes) <= 0){
            ToastUtil.showToast("请输入大于0的金额");
            return;
        }
        /*创建订单*/
        createOrder();
    }

    private void createOrder() {
        showProgress();
        MergePayCode mergePayCode = null;
        if (payCode.equals("UNIONPAY")){
            mergePayCode = MergePayCode.UNIONPAY;
        }else if (payCode.equals("ALIPAY")){
            mergePayCode = MergePayCode.ALIPAY;
        }else if (payCode.equals("WXPAY")){
            mergePayCode = MergePayCode.WXPAY;
        }else if (payCode.equals("GATEWAY")){
            mergePayCode = MergePayCode.GATEWAY;
        }
        Subscriber subscriber = new PosetSubscriber<CreatOrderResponse>().getSubscriber(callback_creatorder);
        UserManager.createOrder(Tool.formatPrice(moneyDes), mergePayCode, subscriber);
    }

    ResponseResultListener callback_creatorder = new ResponseResultListener<CreatOrderResponse>() {
        @Override
        public void success(CreatOrderResponse returnMsg) {
            closeProgress();
            SurePayFragment fragment = SurePayFragment_.builder().build();
            Bundle bundle = new Bundle();
            bundle.putSerializable("orderInfo", returnMsg);
            bundle.putString("orderType", payCode);
            fragment.setArguments(bundle);
            showFragment(fragment);
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

    @Subscribe
    public void finishFragmet(RefreshListener refreshListener){
        if (refreshListener.refresh && refreshListener.tag.equals("finishFragment")){
            finish();
        }
    }
}
