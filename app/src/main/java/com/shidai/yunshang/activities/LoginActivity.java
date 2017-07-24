package com.shidai.yunshang.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.activities.base.BaseActivity;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.LoginResponse;
import com.shidai.yunshang.utils.ToastUtil;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import rx.Subscriber;

/**
 * 创建时间： 2017/7/24.
 * 作者：黄如辉
 * 功能描述：
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;
    @ViewById(R.id.edtTelphone)
    EditText edtTelphone;
    @ViewById(R.id.edtPassword)
    EditText edtPassword;
    @ViewById(R.id.imgSelect)
    ImageView imgSelect;

    private boolean remindPassword = false;

    @AfterViews
    void initView(){
        mNavbar.setDisplayLeftMenu(false);
        mNavbar.setMiddleTitle("登录");
    }

    /*注册*/
    @Click(R.id.txtRegist)
    void regist(){
        Intent intent = new Intent(LoginActivity.this, RegistActivity_.class);
        startActivity(intent);
    }

    /*是否记住密码*/
    @Click(R.id.imgSelect)
    void remindPassword(){
        if (remindPassword){
            remindPassword = false;
            imgSelect.setImageResource(R.drawable.dl_jzmm);
        }else{
            remindPassword = true;
            imgSelect.setImageResource(R.drawable.dl_jzmm_xz);
        }
    }

    /*登录*/
    @Click(R.id.button2)
    void login(){
        String phonenum = edtTelphone.getText().toString();
        String password = edtPassword.getText().toString();

        if (TextUtils.isEmpty(phonenum) || !Tool.checkPhoneNum(phonenum)){
            ToastUtil.showToast("请输入正确的手机号码");
            return;
        }

        if (TextUtils.isEmpty(password)){
            ToastUtil.showToast("请输入密码");
            return;
        }

        showProgress();
        Subscriber subscriber = new PosetSubscriber<LoginResponse>().getSubscriber(callback_login);
        UserManager.login(phonenum, password, subscriber);
    }


    ResponseResultListener callback_login = new ResponseResultListener<LoginResponse>() {
        @Override
        public void success(LoginResponse returnMsg) {
            closeProgress();
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };
}
