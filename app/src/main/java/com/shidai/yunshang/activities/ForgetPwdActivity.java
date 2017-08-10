package com.shidai.yunshang.activities;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.activities.base.BaseActivity;
import com.shidai.yunshang.constants.Constant;
import com.shidai.yunshang.intefaces.EnumSendUserType;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.requests.ForgetPwdRequest;
import com.shidai.yunshang.networks.requests.RegistRequest;
import com.shidai.yunshang.networks.responses.RegistResponse;
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
 * 描述：忘记密码
 * 创建作者： 黄如辉
 * 创建时间： 2017/7/25 17:12
 **/
@EActivity(R.layout.activity_forgetpassword)
public class ForgetPwdActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;
    @ViewById(R.id.edtPhone)
    EditText edtPhone;
    @ViewById(R.id.textView23)
    TextView txtCode;
    @ViewById(R.id.edtPassword)
    EditText edtPassword;
    @ViewById(R.id.edtRePassword)
    EditText edtRePassword;
    @ViewById(R.id.editText)
    EditText editCode;

    private boolean timerstart = false;
    private CountDownTimer myCount;

    @AfterViews
    void initView(){
        mNavbar.setDisplayLeftMenu(false);
        mNavbar.setMiddleTitle("忘记密码");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });
    }

    /*忘记密码*/
    @Click(R.id.button2)
    void regist(){
        String mobile = edtPhone.getText().toString();
        String code = editCode.getText().toString();
        String password = edtPassword.getText().toString();
        String repassword = edtRePassword.getText().toString();

        if (TextUtils.isEmpty(mobile) || !Tool.checkPhoneNum(mobile)){
            ToastUtil.showToast("请输入正确的手机号码");
            return;
        }
        if (TextUtils.isEmpty(code)){
            ToastUtil.showToast("验证码不能为空");
            return;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6 || password.length() > 12){
            ToastUtil.showToast("请输入密码为6-12位的字符串");
            return;
        }
        if (!password.equals(repassword)){
            ToastUtil.showToast("两次密码校验不一致");
            return;
        }

        showProgress();
        ForgetPwdRequest request = new ForgetPwdRequest(mobile, password, repassword, code);
        Subscriber subscribe = new PosetSubscriber<RegistResponse>().getSubscriber(callback_regist);
        UserManager.forgetPwd(request, subscribe);

    }

    /*发送验证码*/
    @Click(R.id.textView23)
    void sendcode(){
        if (!timerstart){
            String mobile = edtPhone.getText().toString();
            if (TextUtils.isEmpty(mobile) || !Tool.checkPhoneNum(mobile)){
                ToastUtil.showToast("请输入正确的手机号码");
                return;
            }
            timerstart = true;
            txtCode.setText(Constant.TIMECOUNT+"s");
            myCount = new MyCount(Constant.TIMECOUNT, 1000).start();
            showProgress();
            Subscriber subscriber =  new PosetSubscriber<Boolean>().getSubscriber(callback_sendcode);
            UserManager.getRegsms(mobile, EnumSendUserType.FORGET, subscriber);
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

    /*獲得验证码*/
    ResponseResultListener callback_sendcode = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            closeProgress();
            if (returnMsg){
                ToastUtil.showToast("获取成功");
            }else{
                ToastUtil.showToast("获取失败");
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };


    ResponseResultListener callback_regist = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            if (returnMsg){
                ToastUtil.showToast("密码修改成功");
                finish();
            }else{
                ToastUtil.showToast("密码修改失败");
            }
            closeProgress();
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myCount !=null){
            myCount.onFinish();
            myCount.cancel();
        }
    }
}
