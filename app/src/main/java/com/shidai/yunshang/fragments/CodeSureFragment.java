package com.shidai.yunshang.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.renderscript.Double2;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.constants.Constant;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.RefreshListener;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.utils.ToastUtil;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import rx.Subscriber;

/**
 * 描述：验证码确认
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/8 14:55
 **/
@EFragment(R.layout.frament_codesure)
public class CodeSureFragment extends BaseFragment {

    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;
    @ViewById(R.id.edtCode)
    EditText edtCode;
    @ViewById(R.id.textView76)
    TextView txtCode;

    private boolean timerstart = false;
    private CountDownTimer myCount;

    @AfterViews
    void initView(){
        mNavbar.setMiddleTitle("验证码确认");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });
    }

    /*快捷支付*/
    @Click(R.id.button2)
    void surSubmit(){
        String code = edtCode.getText().toString();
        if (TextUtils.isEmpty(code)){
            ToastUtil.showToast("请输入验证码");
            return;
        }
        showProgress();
        Subscriber subscriber =  new PosetSubscriber<Double>().getSubscriber(callback_quickpay);
        UserManager.quickPay(code, subscriber);
    }


    /*获取验证码*/
    @Click(R.id.textView76)
    void getCode(){
        if (!timerstart){
            timerstart = true;
            txtCode.setText(Constant.TIMECOUNT+"s");
            myCount = new MyCount(Constant.TIMECOUNT, 1000).start();
            showProgress();
            Subscriber subscriber =  new PosetSubscriber<Boolean>().getSubscriber(callback_sendcode);
            UserManager.getCode( subscriber);
        }
    }

    /*定义一个倒计时的内部类*/
    class MyCount extends CountDownTimer {

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            txtCode.setText( millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            timerstart = false;
            txtCode.setText(getString(R.string.app_login_getcode));
        }
    }


    ResponseResultListener callback_sendcode = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            closeProgress();
            if (returnMsg){
                ToastUtil.showToast("发送成功");
            }else{
                ToastUtil.showToast("发送失败");
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            ToastUtil.showToast("发送失败");
            closeProgress();
        }
    };

    ResponseResultListener callback_quickpay = new ResponseResultListener<Double>() {
        @Override
        public void success(Double returnMsg) {
            closeProgress();
            QuickPayFragment fragment = QuickPayFragment_.builder().build();
            Bundle bundle = new Bundle();
            bundle.putDouble("money", returnMsg);
            fragment.setArguments(bundle);
            showFragment(getActivity(), fragment);
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myCount != null){
            myCount.cancel();
        }
    }
}
