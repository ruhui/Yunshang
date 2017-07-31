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
import com.shidai.yunshang.intefaces.BankFragmentRefresh;
import com.shidai.yunshang.intefaces.EnumSendUserType;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.models.StartAndEndYear;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.requests.SaveCreditResquest;
import com.shidai.yunshang.networks.responses.BankCodeAndNameResponse;
import com.shidai.yunshang.utils.ToastUtil;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.ItemView2;
import com.shidai.yunshang.view.widget.ItemView3;
import com.shidai.yunshang.view.widget.KeyboardPatch;
import com.shidai.yunshang.view.widget.NavBarBack;
import com.shidai.yunshang.wheel.view.TimePickerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import java.util.Date;

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
    ItemView3 itemViewBelongs;
    @ViewById(R.id.itemView5)
    ItemView2 itemViewLastNum;
    @ViewById(R.id.itemView6)
    ItemView3 itemViewEffaceTime;
    @ViewById(R.id.itemView7)
    ItemView2 itemViewPhone;
    @ViewById(R.id.editText)
    EditText edtCode;
    @ViewById(R.id.textView23)
    TextView txtCode;

    private KeyboardPatch keyboard;
    private boolean timerstart = false;
    private CountDownTimer myCount;
    /*有效期*/
    private TimePickerView pvTimecardTime;
    private long bank_id;//银行卡id
    private BankCodeAndNameResponse bankcodeAndName;

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
        itemViewBelongs.setTxtLeft("所属银行");
        itemViewLastNum.setTxtLeft("CVV2");itemViewLastNum.setRightHint("信用卡背面后三位");itemViewLastNum.getEdittext().setInputType(InputType.TYPE_CLASS_NUMBER);itemViewLastNum.getEdittext().setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        itemViewEffaceTime.setTxtLeft("有效期");itemViewEffaceTime.setRightHint("输入有效期 格式：01/16");
        itemViewPhone.setTxtLeft("预留手机号");itemViewPhone.setRightHint("请输入预留手机号");itemViewPhone.getEdittext().setInputType(InputType.TYPE_CLASS_PHONE);

        pvTimecardTime = new TimePickerView(getActivity(), TimePickerView.Type.YEAR_MONTH);

        itemViewEffaceTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvTimecardTime.setTime(new Date());
                pvTimecardTime.setCyclic(true);
                pvTimecardTime.setCancelable(true);
                pvTimecardTime.show();
                Tool.hideInputMethod(getActivity(), itemViewName);
            }

        });


        pvTimecardTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener(){
            @Override
            public void onTimeSelect(Date date)
            {
                String time = Tool.formatCardTime(date);
                itemViewEffaceTime.setEdtRight(time);
            }

            @Override
            public void onTimeSelectStartEndYear(StartAndEndYear startAndEndYear) {

            }
        });


        itemViewCarsfNum.getEdittext().setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容，获取是什么银行的卡
                    getCardBank();
                }
            }
        });
    }

    private void getCardBank() {
        showProgress();
        String bankcode = itemViewCarsfNum.getRightTxt();
        if (TextUtils.isEmpty(bankcode)){
            ToastUtil.showToast("请输入信用卡号");
            return;
        }
        Subscriber subscriber = new PosetSubscriber<BankCodeAndNameResponse>().getSubscriber(callback_bankmsg);
        UserManager.getBankMsg(bankcode, subscriber);
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
        if (!Tool.isRealIDCard(sfCardNum).isrealIDCard()){
            ToastUtil.showToast(Tool.isRealIDCard(sfCardNum).getErrorcode());
            return;
        }
        if (TextUtils.isEmpty(cardNum)){
            ToastUtil.showToast("请输入信用卡号");
            return;
        }
        if (TextUtils.isEmpty(cvv2)){
            ToastUtil.showToast("请输CVV");
            return;
        }
        if (TextUtils.isEmpty(effectTime)){
            ToastUtil.showToast("请输有效期");
            return;
        }
        if (TextUtils.isEmpty(userPhone) || !Tool.checkPhoneNum(userPhone)){
            ToastUtil.showToast("请输正确的预留手机号");
            return;
        }
        if (TextUtils.isEmpty(code) ){
            ToastUtil.showToast("验证码不能为空");
            return;
        }

        if (bankcodeAndName == null){
            ToastUtil.showToast("请重新输入信用卡号");
            return;
        }
        showProgress();
        SaveCreditResquest creditResquest = new SaveCreditResquest(code, bank_id, bankcodeAndName.getBank_name(), cardNum, bankcodeAndName.getBank_code(),
                "CC", userName, sfCardNum, cvv2, userPhone, effectTime, Tool.formatSimpleDate2(new Date()));
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_savecredt);
        UserManager.saveCredit(creditResquest, subscriber);
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
            UserManager.getRegsms(mobile, EnumSendUserType.CARD, subscriber);
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

    ResponseResultListener callback_bankmsg = new ResponseResultListener<BankCodeAndNameResponse>() {
        @Override
        public void success(BankCodeAndNameResponse returnMsg) {
            closeProgress();
            bankcodeAndName = returnMsg;
            itemViewBelongs.setEdtRight(returnMsg.getBank_name());
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();

        }
    };

    ResponseResultListener callback_savecredt = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            closeProgress();
            if (returnMsg){
                EventBus.getDefault().post(new BankFragmentRefresh(true));
                ToastUtil.showToast("保存成功");
                finishFragment();
            }else{
                ToastUtil.showToast("保存失败");
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
