package com.shidai.yunshang.fragments;

import android.os.CountDownTimer;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.constants.Constant;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.EnumSendUserType;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.intefaces.WheelOption1Listener;
import com.shidai.yunshang.intefaces.WheelOption2Listener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.models.RegionInfo;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.BankCodeAndNameResponse;
import com.shidai.yunshang.networks.responses.CityResponse;
import com.shidai.yunshang.utils.ToastUtil;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.ItemView2;
import com.shidai.yunshang.view.widget.ItemView3;
import com.shidai.yunshang.view.widget.ItemView4;
import com.shidai.yunshang.view.widget.KeyboardPatch;
import com.shidai.yunshang.view.widget.NavBarBack;
import com.shidai.yunshang.wheel.view.OptionsPickerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 描述：绑定银行卡
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/1 16:28
 **/
@EFragment(R.layout.fragment_binddebit)
public class BindDebitFragment extends BaseFragment{

    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;
    @ViewById(R.id.itemView1)
    ItemView2 itemCardNum;
    @ViewById(R.id.itemView2)
    ItemView3 itemBelongto;
    @ViewById(R.id.itemView3)
    ItemView4 itemLegon;
    @ViewById(R.id.itemView4)
    ItemView4 itemZhihang;
    @ViewById(R.id.itemView5)
    ItemView2 itemPhone;
    @ViewById(R.id.editText)
    EditText edtCode;
    @ViewById(R.id.textView23)
    TextView txtCode;

    OptionsPickerView pvOptions;
    private boolean timerstart = false;
    private CountDownTimer myCount;
    private KeyboardPatch keyboard;
    private BankCodeAndNameResponse bankcodeAndName;

    private int provincePosition = 0;
    private int cityPosition = 0;
    private int province,city, county;

    private ArrayList<RegionInfo> item1 = new ArrayList<>();
    private ArrayList<ArrayList<RegionInfo>> item2 = new ArrayList<ArrayList<RegionInfo>>();
    private ArrayList<ArrayList<ArrayList<RegionInfo>>> item3 = new ArrayList<ArrayList<ArrayList<RegionInfo>>>();

    @AfterViews
    void initView(){
        //设置键盘弹起
        keyboard = new KeyboardPatch(getActivity(), getView());
        keyboard.enable();
        mNavbar.setMiddleTitle("绑定银行卡");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });

        itemCardNum.setTxtLeft("银行卡号");itemCardNum.setRightHint("请输入银行卡号");itemCardNum.getEdittext().setInputType(InputType.TYPE_CLASS_NUMBER);
        itemBelongto.setTxtLeft("所属银行");
        itemLegon.setTxtLeft("开户地区");
        itemZhihang.setTxtLeft("开户支行");
        itemPhone.setTxtLeft("手机号");itemPhone.setRightHint("请输入手机号");itemPhone.getEdittext().setInputType(InputType.TYPE_CLASS_PHONE);


        itemCardNum.getEdittext().setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
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

        itemLegon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRegions();
            }
        });

    }

    /*初始化三级联动*/
    private void initOptions() {
        // 选项选择器
        pvOptions = new OptionsPickerView(getActivity(), option1Listener1 , option1Listener2);
        // 三级联动效果
        pvOptions.setPicker(item1, item2, item3, true, 0, 0);
        pvOptions.setCyclic(false, false, false);
        pvOptions.setSelectOptions(0, 0, 0);
        // 设置选择的三级单位
        // pwOptions.setLabels("省", "市", "区");
        pvOptions.setTitle("");

        // 设置默认选中的三级项目
        // 监听确定选择按钮
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener()
        {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3)
            {
                String tx = "";
                String itemfirst = "";
                String itemsecond = "";
                String itemthird = "";
                // 返回的分别是三个级别的选中位置
                itemfirst = item1.get(options1).getPickerViewText();
                province = item1.get(options1).getAreaId();
                if (item2.get(options1) != null && item2.get(options1).size() > 0){
                    itemsecond = "  "+ item2.get(options1).get(option2).getPickerViewText();
                    city = item2.get(options1).get(option2).getAreaId();
                }
                if (item3.get(options1).size() > 0 && item3.get(options1).get(option2) != null && item3.get(options1).get(option2).size()>0){
                    itemthird = "  "+  item3.get(options1).get(option2).get(options3).getPickerViewText();
                    county = item3.get(options1).get(option2).get(options3).getAreaId();
                }
                tx = itemfirst + itemsecond + itemthird;
            }
        });
        closeProgress();
    }


    //省选择回调
    WheelOption1Listener option1Listener1 = new WheelOption1Listener() {
        @Override
        public void onItemSelected(int size, int position) {
            if (size == 0){
                int parentid = item1.get(position).getAreaId();
                provincePosition = position;
//                getCity(parentid);
            }
            cityPosition = 0;
        }
    };

    //市选择回调
    WheelOption2Listener option1Listener2 = new WheelOption2Listener() {
        @Override
        public void onItemSelected(int size, int index) {
            if (size == 0 && index != 0){
                cityPosition = index;
                int parentid = item2.get(provincePosition).get(index).getAreaId();
//                getCounty(parentid);
            }
        }
    };


    private void getRegions() {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<List<CityResponse>>().getSubscriber(callback_getregions);
        UserManager.getRegions(subscriber);
    }


    /*发送验证码*/
    @Click(R.id.textView23)
    void sendcode(){
        if (!timerstart){
            String mobile =  itemPhone.getRightTxt();
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


    private void getCardBank() {
        showProgress();
        String bankcode = itemCardNum.getRightTxt();
        if (TextUtils.isEmpty(bankcode)){
            ToastUtil.showToast("请输入信用卡号");
            return;
        }
        Subscriber subscriber = new PosetSubscriber<BankCodeAndNameResponse>().getSubscriber(callback_bankmsg);
        UserManager.getBankMsg(bankcode, subscriber);
    }


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
            itemBelongto.setEdtRight(returnMsg.getBank_name());
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();

        }
    };


    ResponseResultListener callback_getregions = new ResponseResultListener<List<CityResponse>>() {
        @Override
        public void success(List<CityResponse> returnMsg) {
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
        if (keyboard != null){
            keyboard.disable();
        }
        if (myCount !=null){
            myCount.onFinish();
            myCount.cancel();
        }
    }
}
