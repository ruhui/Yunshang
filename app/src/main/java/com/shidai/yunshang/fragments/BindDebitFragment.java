package com.shidai.yunshang.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shidai.greendao.DaoMaster;
import com.shidai.greendao.RegionInfoDao;
import com.shidai.yunshang.R;
import com.shidai.yunshang.constants.Constant;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.EnumSendUserType;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.intefaces.WheelOption1Listener;
import com.shidai.yunshang.intefaces.WheelOption2Listener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.models.BranchbrankModel;
import com.shidai.yunshang.models.CityChild;
import com.shidai.yunshang.models.CountyMode;
import com.shidai.yunshang.models.RegionInfo;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.requests.SaveDebitRequest;
import com.shidai.yunshang.networks.responses.BankCodeAndNameResponse;
import com.shidai.yunshang.networks.responses.CityResponse;
import com.shidai.yunshang.utils.GreenDaoUtils;
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
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    /*开户支行*/
    private BranchbrankModel branchbrankModel;
    private String bankid = "0";

    private int provincePosition = 0;
    private int cityPosition = 0;
    private int province,city, county;
    private List<CityResponse> cityCach = new ArrayList<>();

    private ArrayList<RegionInfo> item1 = new ArrayList<>();
    private ArrayList<ArrayList<RegionInfo>> item2 = new ArrayList<ArrayList<RegionInfo>>();
    private ArrayList<ArrayList<ArrayList<RegionInfo>>> item3 = new ArrayList<ArrayList<ArrayList<RegionInfo>>>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

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
                Tool.hideInputMethod(getActivity(), itemPhone);
                if (pvOptions != null){
                    pvOptions.setPicker(item1, item2, item3, false, provincePosition, cityPosition);
                    pvOptions.setSelectOptions(provincePosition, cityPosition);
                    pvOptions.show();
                    closeProgress();
                }else{
                    getRegions();
                }
            }
        });


        itemZhihang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tool.hideInputMethod(getActivity(), itemPhone);
                String address = itemLegon.getRightTxt();
                if (TextUtils.isEmpty(address)){
                    ToastUtil.showToast("请选择开户地区");
                    return;
                }
                if (bankcodeAndName == null){
                    ToastUtil.showToast("请输入银行卡号");
                    return;
                }
                KHZHFragment fragment = KHZHFragment_.builder().build();
                Bundle bundle = new Bundle();
                bundle.putString("bankcode", bankcodeAndName.getBank_code());
                bundle.putString("provinceid", province+"");
                bundle.putString("cityid", city+"");
                fragment.setArguments(bundle);
                showFragment(getActivity(), fragment);
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
                itemLegon.setEdtRight(tx);
            }
        });
        pvOptions.show();
        closeProgress();
    }


    //省选择回调
    WheelOption1Listener option1Listener1 = new WheelOption1Listener() {
        @Override
        public void onItemSelected(int size, int position) {
            provincePosition = position;
            cityPosition = 0;
            /*获取县*/
            getCountry();
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
            cityPosition = index;
            getCountry();
        }
    };

    private void getCountry() {
        List<CountyMode> list_cityChild =  cityCach.get(provincePosition).getChildren().get(cityPosition).getChildren();
        ArrayList<RegionInfo> regionInfos = new ArrayList<RegionInfo>();
        for (CountyMode coutryMode : list_cityChild){
            RegionInfo regionInfo_country = new RegionInfo(coutryMode.getId(), cityCach.get(provincePosition).getId(), coutryMode.getFull_name(),
                    coutryMode.getName(), coutryMode.getRegion_name(), coutryMode.getFirst_letter());
            regionInfos.add(regionInfo_country);
        }

        if (item3.get(provincePosition).size() == 0){
            item3.get(provincePosition).add(cityPosition, regionInfos);
        }else{
            if ( item3.get(provincePosition).get(cityPosition).size() == 0){
                item3.get(provincePosition).add(cityPosition, regionInfos);
            }else{
                item3.get(provincePosition).remove(cityPosition);
                item3.get(provincePosition).add(cityPosition, regionInfos);
            }
        }

        pvOptions.setPicker(item1, item2, item3, false, provincePosition, cityPosition);
        pvOptions.setSelectOptions(provincePosition, cityPosition);
    }


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


    /*提交*/
    @Click(R.id.button2)
    void commitData(){
        String cardNum = itemCardNum.getRightTxt();
        String address = itemLegon.getRightTxt();
        String zhihang = itemZhihang.getRightTxt();
        String txtPhone = itemPhone.getRightTxt();
        String code = edtCode.getText().toString();
        if (TextUtils.isEmpty(cardNum)){
            ToastUtil.showToast("请输入银行卡号");
            return;
        }
        if (bankcodeAndName == null){
            ToastUtil.showToast("请重新输入银行卡号");
            return;
        }
        if (TextUtils.isEmpty(address)){
            ToastUtil.showToast("请选择开户地区");
            return;
        }
        if (TextUtils.isEmpty(zhihang)){
            ToastUtil.showToast("请选择开户支行");
            return;
        }
        if (TextUtils.isEmpty(txtPhone) || !Tool.checkPhoneNum(txtPhone)){
            ToastUtil.showToast("请输入正确的手机号码");
            return;
        }
        if (TextUtils.isEmpty(code)){
            ToastUtil.showToast("请输入验证码");
            return;
        }
        showProgress();

        SaveDebitRequest request = new SaveDebitRequest(code, bankid, cardNum, bankcodeAndName.getBank_code(),
                province+"", city+"", county+"", address, txtPhone, bankcodeAndName.getBank_name(), branchbrankModel.getBranch_name());

        Subscriber subscriber = new PosetSubscriber<BankCodeAndNameResponse>().getSubscriber(callback_savedebit);
        UserManager.saveDebit(request, subscriber);
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
            cityCach.addAll(returnMsg);
            new GetAddressTask(returnMsg).execute("");
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


    class GetAddressTask extends AsyncTask<String, Void, String>{

        private List<CityResponse> returnMsg = new ArrayList<>();

        public GetAddressTask(List<CityResponse> returnMsg) {
            this.returnMsg = returnMsg;
        }

        @Override
        protected String doInBackground(String... params) {
            List<RegionInfo> reginList = null;
            RegionInfoDao regionInfoDao = GreenDaoUtils.getSingleTon().getmDaoSession().getRegionInfoDao();
            if (regionInfoDao.count() > 0) {
                reginList = regionInfoDao.queryBuilder()
                        .where(RegionInfoDao.Properties.ParentId.eq(0))
                        .build().list();
            }

            if (reginList == null || reginList.size() == 0 ){
                for (CityResponse cityResponse : returnMsg){
                    /*省*/
                    RegionInfo regionInfo_pro = new RegionInfo(cityResponse.getId(), 0, cityResponse.getFull_name(),
                            cityResponse.getName(), cityResponse.getRegion_name(), cityResponse.getFirst_letter());
                    regionInfoDao.insert(regionInfo_pro);
                    for (CityChild cityChild : cityResponse.getChildren()){
                        //市
                        RegionInfo regionInfo_city = new RegionInfo(cityChild.getId(), cityResponse.getId(), cityChild.getFull_name(),
                                cityChild.getName(), cityChild.getRegion_name(), cityChild.getFirst_letter());
                        regionInfoDao.insert(regionInfo_city);

//                        for (CountyMode countyMode : cityChild.getChildren()){
//                            //县
//                            RegionInfo regionInfo_countyr = new RegionInfo(countyMode.getId(), cityChild.getId(), countyMode.getFull_name(),
//                                    countyMode.getName(), countyMode.getRegion_name(), countyMode.getFirst_letter());
//                            regionInfoDao.insert(regionInfo_countyr);
//                        }
                    }
                }

            }

            //查找省市县
            reginList = regionInfoDao.queryBuilder()
                    .where(RegionInfoDao.Properties.ParentId.eq(0))
                    .build().list();
            //省
            item1.addAll(reginList);
            for (RegionInfo reginInfo : item1){
                ArrayList<RegionInfo> reginstList_city = new ArrayList<>();
                List<RegionInfo> reginList_city = regionInfoDao.queryBuilder()
                        .where(RegionInfoDao.Properties.ParentId.eq(reginInfo.getAreaId()))
                        .build().list();
                //市
                reginstList_city.addAll(reginList_city);
                item2.add(reginstList_city);
            }


            for (CityResponse res : returnMsg){
                ArrayList<ArrayList<RegionInfo>> lis = new ArrayList<ArrayList<RegionInfo>>();
                for (CityChild city : res.getChildren()){
                    ArrayList<RegionInfo> list_reg = new ArrayList<>();
                    lis.add(list_reg);
                    item3.add(lis);
                }
            }


            CityChild cityChild =  returnMsg.get(0).getChildren().get(0);
            ArrayList<ArrayList<RegionInfo>> lis = new ArrayList<>();
            RegionInfo regionInfo_country = new RegionInfo(cityChild.getId(), returnMsg.get(0).getId(), cityChild.getFull_name(),
                    cityChild.getName(), cityChild.getRegion_name(), cityChild.getFirst_letter());
            ArrayList<RegionInfo> regionInfos = new ArrayList<RegionInfo>();
            regionInfos.add(regionInfo_country);
            lis.add(regionInfos);
            item3.get(0).add(0, regionInfos);

//            for (ArrayList<RegionInfo> listRegion : item2){
//                for (RegionInfo regionInfo : listRegion) {
//                    ArrayList<ArrayList<RegionInfo>> list_list_country = new ArrayList<>();
//                    ArrayList<RegionInfo> list_country = new ArrayList<RegionInfo>();
//                    List<RegionInfo> reginList_country = regionInfoDao.queryBuilder()
//                            .where(RegionInfoDao.Properties.ParentId.eq(regionInfo.getAreaId()))
//                            .build().list();
//                    list_country.addAll(reginList_country);
//                    list_list_country.add(list_country);
//                    //县
//                    item3.add(list_list_country);
//                }
//            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            closeProgress();
            initOptions();
        }
    }


    ResponseResultListener callback_savedebit = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            closeProgress();
            if (returnMsg){
                ToastUtil.showToast("添加成功");
                finishFragment();
            }else{
                ToastUtil.showToast("添加失败");
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

    @Subscribe
    public void setKHZH(BranchbrankModel model){
        if (model != null){
            branchbrankModel = model;
            itemZhihang.setEdtRight(model.getBranch_name());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
