package com.shidai.yunshang.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.activities.base.BaseActivity;
import com.shidai.yunshang.adapters.WalletTixianAdapter;
import com.shidai.yunshang.intefaces.AdapterListener;
import com.shidai.yunshang.intefaces.RefreshListener;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.SettletypeResponse;
import com.shidai.yunshang.networks.responses.TransferResponse;
import com.shidai.yunshang.utils.SecurePreferences;
import com.shidai.yunshang.utils.ToastUtil;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

@EActivity(R.layout.fragment_tixianwallet)
public class TixianWalletActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;
    @ViewById(R.id.editText2)
    EditText edtMoney;
    @ViewById(R.id.textView44)
    TextView txtAll;
    @ViewById(R.id.textView48)
    TextView txtRemak;
    @ViewById(R.id.mRecycleView)
    RecyclerView mRecycleView;

    private ArrayList<SettletypeResponse> listResponse = new ArrayList<>();
    private WalletTixianAdapter adapter_wallettixian;
    private String tixianMoney="";
    private String setType = "";
    /*当日提现*/
    private double single_quota = 0;

    @AfterViews
    void initView(){
        tixianMoney = getIntent().getStringExtra("tixianMoney");
        mNavbar.setMiddleTitle("提现");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });

        /*获取提现结算方式*/
        getSettletype();

        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setAdapter(adapter_wallettixian = new WalletTixianAdapter(adapterListener));

        //String mintransfer = SecurePreferences.getInstance().getString("MINTRANSFER", "");

        txtAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtMoney.setText(tixianMoney);
            }
        });
    }

    /*选择费率*/
    AdapterListener adapterListener = new AdapterListener<SettletypeResponse>() {
        @Override
        public void setItemClickListener(SettletypeResponse o, int position) {
            setType = o.getSettle_type();
            single_quota = o.getSingleQuota();
            for (SettletypeResponse response : listResponse){
                response.setClick(false);
            }
            for (int i =0;i<listResponse.size();i++){
                if (position == i){
                    SettletypeResponse settletypeResponse = listResponse.get(i);
                    settletypeResponse.setClick(true);
                    break;
                }
            }
            adapter_wallettixian.replaceWith(listResponse);

            txtRemak.setText("单次提现不少于" + o.getSingle_quota() + ",不高于" + o.getCard_quota() + "   可提现金额: ¥"+ Tool.formatPrice(tixianMoney));
        }
    };

    private void getSettletype() {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<List<SettletypeResponse>>().getSubscriber(callback_settletype);
        UserManager.getSettletype(subscriber);
    }

    /*提现*/
    @Click(R.id.button2)
    void commitTixian(){
        String inputMoney = edtMoney.getText().toString();
        if (TextUtils.isEmpty(setType)){
            ToastUtil.showToast("请选择结算类型");
            return;
        }
        if (TextUtils.isEmpty(inputMoney)){
            ToastUtil.showToast("请输入金额");
            return;
        }
        if (Double.valueOf(inputMoney) == 0 || Double.valueOf(inputMoney) < Double.valueOf(single_quota)){
            ToastUtil.showToast("单次提现不少于¥"+single_quota );
            return;
        }
        showProgress();
        Subscriber subscribe = new PosetSubscriber<TransferResponse>().getSubscriber(callback_transfer);
        UserManager.setTransfer(Double.valueOf(inputMoney), setType, 1, subscribe);
    }


    ResponseResultListener callback_settletype = new ResponseResultListener<List<SettletypeResponse>>() {
        @Override
        public void success(List<SettletypeResponse> returnMsg) {
            closeProgress();
            listResponse.clear();
            listResponse.addAll(returnMsg);
            adapter_wallettixian.clear();
            adapter_wallettixian.addAll(listResponse);
            for (SettletypeResponse response : returnMsg){
                if (response.getSettle_type().equals("T0")){
                    txtRemak.setText("单次提现不少于" + response.getSingle_quota() + ",不高于" + response.getCard_quota() +"   可提现金额: ¥"+ Tool.formatPrice(tixianMoney));
                }
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };


    ResponseResultListener callback_transfer = new ResponseResultListener<TransferResponse>() {
        @Override
        public void success(TransferResponse returnMsg) {
            closeProgress();
            EventBus.getDefault().post(new RefreshListener(true));
            Intent intent = new Intent(getActivity(), SuccessTixianActivity_.class);
            intent.putExtra("tranferRes", returnMsg);
            startActivity(intent);
            finish();
//            SuccessTixianActivity fragment_tranfer = SuccessTixianFragment_.builder().build();
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("tranferRes", returnMsg);
//            fragment_tranfer.setArguments(bundle);
//            showFragment(getActivity(), fragment_tranfer);

        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };
}
