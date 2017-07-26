package com.shidai.yunshang.fragments;

import android.os.CountDownTimer;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.activities.RegistActivity;
import com.shidai.yunshang.constants.Constant;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.EnumSendUserType;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.utils.ToastUtil;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.ItemView2;
import com.shidai.yunshang.view.widget.KeyboardPatch;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import rx.Subscriber;

/**
 * 创建时间： 2017/7/26.
 * 作者：黄如辉
 * 功能描述：绑定信用卡
 */
@EFragment(R.layout.fragment_bindcred)
public class BindCreditFragment extends BaseFragment {

    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;
    @ViewById(R.id.itemView1)
    ItemView2 itemViewName;
    @ViewById(R.id.itemView2)
    ItemView2 itemViewCardNum;
    @ViewById(R.id.itemView3)
    ItemView2 itemViewCarsfNum;
    @ViewById(R.id.itemView4)
    ItemView2 itemViewBelongs;
    @ViewById(R.id.itemView5)
    ItemView2 itemViewLastNum;
    @ViewById(R.id.itemView6)
    ItemView2 itemViewEffaceTime;
    @ViewById(R.id.itemView7)
    ItemView2 itemViewPhone;
    @ViewById(R.id.editText)
    EditText edtCode;
    @ViewById(R.id.textView23)
    TextView txtCode;

    private KeyboardPatch keyboard;
    private boolean timerstart = false;
    private CountDownTimer myCount;

    @AfterViews
    void initView(){
        //设置键盘弹起
        keyboard = new KeyboardPatch(getActivity(), getView());
        keyboard.enable();
        mNavbar.setMiddleTitle("绑定信用卡");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });
        itemViewName.setTxtLeft("持卡人");itemViewName.setRightHint("请输入持卡人姓名");
        itemViewCardNum.setTxtLeft("身份证号");itemViewCardNum.setRightHint("请输入身份证号码");
        itemViewCarsfNum.setTxtLeft("信用卡号");itemViewCarsfNum.setRightHint("请输入信用卡号");itemViewCarsfNum.getEdittext().setInputType(InputType.TYPE_CLASS_NUMBER);
        itemViewBelongs.setTxtLeft("所属银行");itemViewBelongs.setEditEable(false);
        itemViewLastNum.setTxtLeft("CVV2");itemViewLastNum.setRightHint("信用卡背面后三位");itemViewLastNum.getEdittext().setInputType(InputType.TYPE_CLASS_NUMBER);itemViewLastNum.getEdittext().setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        itemViewEffaceTime.setTxtLeft("有效期");itemViewEffaceTime.setRightHint("输入有效期 格式：01/16");
        itemViewPhone.setTxtLeft("预留手机号");itemViewPhone.setRightHint("请输入预留手机号");itemViewPhone.getEdittext().setInputType(InputType.TYPE_CLASS_PHONE);
    }

    /*提交*/
    @Click(R.id.button2)
    void submitBtn(){
        String userName = itemViewName.getRightTxt();
        String sfCardNum = itemViewCardNum.getRightTxt();
        String cardNum = itemViewCarsfNum.getRightTxt();
        String belongTo = itemViewBelongs.getRightTxt();
        String cvv2 = itemViewLastNum.getRightTxt();
        String effectTime = itemViewEffaceTime.getRightTxt();
        String userPhone = itemViewPhone.getRightTxt();
        String code = edtCode.getText().toString();

        if (TextUtils.isEmpty(userName)){
            ToastUtil.showToast("请输入持卡人姓名");
            return;
        }
        if (Tool.isRealIDCard(sfCardNum).isrealIDCard()){
            ToastUtil.showToast(Tool.isRealIDCard(sfCardNum).getErrorcode());
            return;
        }
        if (TextUtils.isEmpty(cardNum)){
            ToastUtil.showToast("请输入信用卡号");
            return;
        }
    }

    /*发送验证码*/
    @Click(R.id.textView23)
    void sendcode(){
        if (!timerstart){
            String mobile =  itemViewPhone.getRightTxt();
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



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (keyboard != null){
            keyboard.disable();
        }
        if (myCount !=null){
            myCount.onFinish();
            myCount.cancel();
        }
    }
}
