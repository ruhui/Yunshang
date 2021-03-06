package com.shidai.yunshang.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shidai.yunshang.activities.InputMoneyActivity;
import com.shidai.yunshang.adapters.SurePayAdapter;
import com.shidai.yunshang.intefaces.AdapterListener;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.models.ChannelModel;
import com.shidai.yunshang.models.SuerPayModel;
import com.shidai.yunshang.R;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.CreatOrderResponse;
import com.shidai.yunshang.networks.responses.CreatQcodeResponse;
import com.shidai.yunshang.utils.ToastUtil;
import com.shidai.yunshang.view.widget.FullyLinearLayoutManager;
import com.shidai.yunshang.view.widget.NavBarBack;
import com.shidai.yunshang.view.widget.SpaceItemDecoration;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 描述：确认支付
 * 创建作者： 黄如辉
 * 创建时间： 2017/7/21 10:53
 **/

@EFragment(R.layout.fragment_surepay)
public class SurePayFragment extends BaseFragment{

    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;
    /*订单号*/
    @ViewById(R.id.textView4)
    TextView txtOrderId;
    /*交易类型*/
    @ViewById(R.id.textView6)
    TextView txtType;
    /*交易金额*/
    @ViewById(R.id.textView8)
    TextView txtMoney;

    @ViewById(R.id.mRecycleView)
    RecyclerView mRecycleView;

    private String orderType = "";
    private SurePayAdapter adapter_surepay;
    private CreatOrderResponse orderInfo;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        orderInfo = (CreatOrderResponse) getArguments().getSerializable("orderInfo");
        orderType = getArguments().getString("orderType");
    }

    @AfterViews
    void initView(){

        ((InputMoneyActivity)getActivity()).setErweimaShow();

        mNavbar.setBarTitle("确认支付");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });

        txtOrderId.setText(orderInfo.getOrder_no());
        txtType.setText(orderInfo.getPay_name());
        txtMoney.setText("¥"+orderInfo.getAmount());

        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(getActivity());
        manager.setSmoothScrollbarEnabled(true);
        mRecycleView.setLayoutManager(manager);
        mRecycleView.setAdapter(adapter_surepay = new SurePayAdapter(adapterListener));
        adapter_surepay.setPaycode(orderInfo.getPay_code());
        adapter_surepay.addAll(orderInfo.getChannel());
    }


    AdapterListener adapterListener = new AdapterListener<ChannelModel>() {
        @Override
        public void setItemClickListener(ChannelModel suerPayModel, int position) {
            if (orderType.equals("UNIONPAY")){
                //选择银行卡
                selectChannel(suerPayModel.getPay_channel(), suerPayModel.getSettle_type());
            }else if (orderType.equals("ALIPAY_JS") || orderType.equals("WXPAY_JS")){
                //扫码支付
                selectSMchannel(suerPayModel.getPay_channel(), suerPayModel.getSettle_type());
            }else if (orderType.equals("GATEWAY")){
            }
        }
    };

    private void selectSMchannel(int pay_channel, String settle_type) {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<CreatQcodeResponse>().getSubscriber(callback_selectsmchannel);
        UserManager.createQrode(String.valueOf(pay_channel), settle_type, subscriber);
    }

    private void selectChannel(int pay_channel, String settle_type) {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_selectchannel);
        UserManager.selectChannel(String.valueOf(pay_channel), settle_type, subscriber);
    }

    ResponseResultListener callback_selectchannel = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            //ALIPAY_QR, WXPAY_QR, JDPAY_QR, WXPAY_JS, ALIPAY_JS, UNIONPAY, GATEWAY
            closeProgress();
            if (returnMsg){
                if (orderType.equals("UNIONPAY")){
                    //选择银行卡
                    showFragment(getActivity(), SelectBankcardFragment_.builder().build());
                }else if (orderType.equals("ALIPAY_JS") || orderType.equals("WXPAY_JS")){
                    //扫码支付
                }else if (orderType.equals("GATEWAY")){
                }
            }else{
                ToastUtil.showToast("数据异常");
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
            ToastUtil.showToast("数据异常");
        }
    };

    /*扫码选择通道的回调*/
    ResponseResultListener callback_selectsmchannel = new ResponseResultListener<CreatQcodeResponse>() {
        @Override
        public void success(CreatQcodeResponse returnMsg) {
            closeProgress();
            SaomaPayFragment fragment = SaomaPayFragment_.builder().build();
            Bundle bundle = new Bundle();
            bundle.putString("payType", orderType);
            bundle.putSerializable("qcodeData", returnMsg);
            fragment.setArguments(bundle);
            showFragment(getActivity(), fragment);
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };
}
