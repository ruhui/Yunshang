package com.shidai.yunshang.activities;

import android.os.CountDownTimer;
import android.text.TextUtils;
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
import org.greenrobot.eventbus.Subscribe;

import rx.Subscriber;

/**
 * 创建时间： 2017/7/24.
 * 作者：黄如辉
 * 功能描述：注册
 */
@EActivity(R.layout.activity_regist)
public class RegistActivity extends BaseActivity {

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
    @ViewById(R.id.imgSelect)
    ImageView imgSelect;
    @ViewById(R.id.edtRecommender)
    EditText edtRecommender;
    @ViewById(R.id.editText)
    EditText editCode;

    private boolean timerstart = false;
    private boolean selectXieyi = false;
    private CountDownTimer myCount;


    @AfterViews
    void initView(){
        mNavbar.setDisplayLeftMenu(false);
        mNavbar.setMiddleTitle("注册");
    }

    @Click(R.id.imgSelect)
    void selectXieyi(){
        if (selectXieyi){
            selectXieyi = false;
            imgSelect.setImageResource(R.drawable.dl_jzmm);
        }else{
            selectXieyi = true;
            imgSelect.setImageResource(R.drawable.dl_jzmm_xz);
        }
    }

    @Click(R.id.txtRegist)
    void login(){
        finish();
    }

    /*注册*/
    @Click(R.id.button2)
    void regist(){
        String mobile = edtPhone.getText().toString();
        String code = editCode.getText().toString();
        String password = edtPassword.getText().toString();
        String repassword = edtRePassword.getText().toString();
        String recommender = edtRecommender.getText().toString();

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
        if (!selectXieyi){
            ToastUtil.showToast("请勾选注册协议");
            return;
        }

        if (!TextUtils.isEmpty(recommender)){
            if (!Tool.checkPhoneNum(recommender)){
                ToastUtil.showToast("请输入正确的邀请人手机号");
                return;
            }
        }


        showProgress();
        RegistRequest registRequest = new RegistRequest( mobile,  password,  repassword,  code,  recommender);
        Subscriber subscribe = new PosetSubscriber<RegistResponse>().getSubscriber(callback_regist);
        UserManager.RegistUser(registRequest, subscribe);

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
            UserManager.getRegsms(mobile, EnumSendUserType.REGISTER, subscriber);
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


    /*注册回调*/
    ResponseResultListener callback_regist = new ResponseResultListener<RegistResponse>() {
        @Override
        public void success(RegistResponse returnMsg) {
            closeProgress();
            ToastUtil.showToast("注册成功");
            String phonenum = edtPhone.getText().toString();
            String password = edtPassword.getText().toString();
            SecurePreferences.getInstance().edit().putString("Authorization", returnMsg.access_token).commit();
            SecurePreferences.getInstance().edit().putString("USERMOBILE", phonenum).commit();
            SecurePreferences.getInstance().edit().putString("USERPASSWORD", password).commit();
            SecurePreferences.getInstance().edit().putString("EXPIRESDATE", returnMsg.expires_date).commit();
            finish();
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
