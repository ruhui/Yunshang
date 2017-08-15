package com.shidai.yunshang.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.activities.base.BaseActivity;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.LoginResponse;
import com.shidai.yunshang.utils.SecurePreferences;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                    return;
                }
            }
        }
    }

    @AfterViews
    void initView(){
        mNavbar.setDisplayLeftMenu(false);
        mNavbar.setMiddleTitle("登录");

        remindPassword = SecurePreferences.getInstance().getBoolean("REMINDPASSWORD", false);
        if (remindPassword){
            String phone = SecurePreferences.getInstance().getString("USERMOBILE", "");
            String userpassword = SecurePreferences.getInstance().getString("USERPASSWORD", "");
            edtTelphone.setText(phone);
            edtPassword.setText(userpassword);
            imgSelect.setImageResource(R.drawable.dl_jzmm_xz);
        }else{
            imgSelect.setImageResource(R.drawable.dl_jzmm);
        }
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

    /*忘记密码*/
    @Click(R.id.textView22)
    void forgetPassword(){
        Intent intent = new Intent(LoginActivity.this, ForgetPwdActivity_.class);
        startActivity(intent);
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
            ToastUtil.showToast("登录成功");
            SecurePreferences.getInstance().edit().putString("Authorization", returnMsg.getAccess_token()).commit();
            SecurePreferences.getInstance().edit().putString("EXPIRESDATE", returnMsg.getExpires_date()).commit();
            if (remindPassword){
                String phonenum = edtTelphone.getText().toString();
                String password = edtPassword.getText().toString();
                SecurePreferences.getInstance().edit().putString("USERMOBILE", phonenum).commit();
                SecurePreferences.getInstance().edit().putString("USERPASSWORD", password).commit();
                SecurePreferences.getInstance().edit().putBoolean("REMINDPASSWORD", true).commit();
            }else{
                SecurePreferences.getInstance().edit().putString("USERMOBILE", "").commit();
                SecurePreferences.getInstance().edit().putString("USERPASSWORD", "").commit();
                SecurePreferences.getInstance().edit().putBoolean("REMINDPASSWORD", false).commit();
            }

            Intent intent = new Intent(LoginActivity.this, MainActivity_.class);
            startActivity(intent);
            finish();
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };
}
