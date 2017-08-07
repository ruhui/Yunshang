package com.shidai.yunshang.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.activities.TixianWalletActivity_;
import com.shidai.yunshang.adapters.WalletAdapter;
import com.shidai.yunshang.adapters.WalletTitleAdapter;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.AdapterListener;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.models.BillbagModel;
import com.shidai.yunshang.models.ChannelModel;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.BankmsgResponse;
import com.shidai.yunshang.networks.responses.BillbagResponse;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.NavBarWallet;
import com.shidai.yunshang.view.widget.SwitchPayTypeView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import rx.Subscriber;

/**
 * 创建时间： 2017/7/28.
 * 作者：黄如辉
 * 功能描述：钱包
 */
@EFragment(R.layout.fragment_wallet)
public class WalletFragment extends BaseFragment{
    /*可提现金额*/
    @ViewById(R.id.txtMoney)
    TextView txtMoney;
    /*共累计收款*/
    @ViewById(R.id.txtShouKuan)
    TextView txtShouKuan;
    /*提现*/
    @ViewById(R.id.button2)
    Button btnTixian;
    @ViewById(R.id.mRecycleView)
    RecyclerView mRecycleView;
    @ViewById(R.id.titleRecycle)
    RecyclerView titleRecycle;

    @ViewById(R.id.mNavbar)
    NavBarWallet navBarWallet;

    private WalletAdapter adapter_wallet;
    private BillbagResponse billBagReturnMsg;
    private WalletTitleAdapter walletTitleAdapter;

    @AfterViews
    void initView(){
        navBarWallet.setLeftMenuIcon(R.drawable.sy_zd);
        navBarWallet.setLeftText("账单");
        navBarWallet.setMiddleTitle("钱包");
        navBarWallet.setIvMenuRight(R.drawable.qb_squan);
        navBarWallet.setTxtRightView("授权");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        titleRecycle.setLayoutManager(linearLayoutManager);
        titleRecycle.setAdapter(walletTitleAdapter = new WalletTitleAdapter(adapterListener));

         /*获取钱包*/
        getBillbag();

//        /*银联支付*/
//        mSwitchPayTypeView.setYLPayLisener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mSwitchPayTypeView.showYLline();
//                if (billBagReturnMsg == null){
//                    return;
//                }
//                for (BillbagModel billbagModel : billBagReturnMsg.getPayments()){
//                    if (billbagModel.getCode().equals("UNIONPAY")){
//                        //默认银联
//                        adapter_wallet.setType(billbagModel.getCode());
//                        adapter_wallet.clear();
//                        adapter_wallet.addAll(billbagModel.getChannel());
//                    }
//                }
//            }
//        });
//
//        /*支付宝*/
//        mSwitchPayTypeView.setZFBPayLisener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mSwitchPayTypeView.showZFBline();
//                if (billBagReturnMsg == null){
//                    return;
//                }
//                for (BillbagModel billbagModel : billBagReturnMsg.getPayments()){
//                    if (billbagModel.getCode().equals("ALIPAY")){
//                        //默认银联
//                        adapter_wallet.setType(billbagModel.getCode());
//                        adapter_wallet.clear();
//                        adapter_wallet.addAll(billbagModel.getChannel());
//                    }
//                }
//            }
//        });
//
//        /*微信支付*/
//        mSwitchPayTypeView.setWXPayLisener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mSwitchPayTypeView.showWXline();
//                if (billBagReturnMsg == null){
//                    return;
//                }
//                for (BillbagModel billbagModel : billBagReturnMsg.getPayments()){
//                    if (billbagModel.getCode().equals("WXPAY")){
//                        //默认银联
//                        adapter_wallet.setType(billbagModel.getCode());
//                        adapter_wallet.clear();
//                        adapter_wallet.addAll(billbagModel.getChannel());
//                    }
//                }
//            }
//        });


        navBarWallet.setOnMenuClickListener(new NavBarWallet.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                /*账单*/
            }

            @Override
            public void onRightMenuClick(View view) {
                super.onRightMenuClick(view);
                /*更多授权*/
                showFragment(getActivity(), AuthorizationFragment_.builder().build());
            }
        });

    }


    AdapterListener adapterListener = new AdapterListener<BillbagModel>() {
        @Override
        public void setItemClickListener(BillbagModel o, int position) {
            if (billBagReturnMsg != null){
                for (BillbagModel billbagModel : billBagReturnMsg.getPayments()){
                    billbagModel.setClick(false);
                }
                for (int i=0; i<billBagReturnMsg.getPayments().size(); i++){
                    BillbagModel billbagModel = billBagReturnMsg.getPayments().get(i);
                    if (position == i){
                        billbagModel.setClick(true);
                        break;
                    }
                }
                walletTitleAdapter.replaceWith(billBagReturnMsg.getPayments());
                BillbagModel billbagModel = billBagReturnMsg.getPayments().get(position);
                List<ChannelModel>  listChannel = billbagModel.getChannel();
                adapter_wallet.clear();
                adapter_wallet.addAll(listChannel);
            }
        }
    };

    private void setView() {
//        mSwitchPayTypeView.showYLline();
        if (billBagReturnMsg == null){
            return;
        }

        walletTitleAdapter.addAll(billBagReturnMsg.getPayments());

        txtMoney.setText("¥"+ Tool.formatPrice(billBagReturnMsg.getDeposit()));
        txtShouKuan.setText("共累计收款："+Tool.formatPrice(billBagReturnMsg.getTotal_receipt()));

        //默认显示银联支付
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setAdapter(adapter_wallet = new WalletAdapter());

        for (BillbagModel billbagModel : billBagReturnMsg.getPayments()){
            if (billbagModel.getCode().equals("UNIONPAY")){
                //默认银联
                adapter_wallet.clear();
                adapter_wallet.addAll(billbagModel.getChannel());
            }
        }
    }

    /*提现*/
    @Click(R.id.button2)
    void tiXian(){
        String tixianMoney = txtMoney.getText().toString();
        Intent intent = new Intent(getActivity(), TixianWalletActivity_.class);
        intent.putExtra("tixianMoney", Tool.formatPrice(billBagReturnMsg.getDeposit()));
        startActivity(intent);
//        TixianWalletFragment fragment = TixianWalletFragment_.builder().build();
//        Bundle bundle = new Bundle();
//        bundle.putString("tixianMoney", Tool.formatPrice(billBagReturnMsg.getDeposit()));
//        fragment.setArguments(bundle);
//        showFragment(getActivity(),fragment);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            /*获取钱包*/
            getBillbag();
        }
    }

    private void getBillbag() {
        Subscriber subscriber = new PosetSubscriber<BillbagResponse>().getSubscriber(callback_getbillbag);
        UserManager.getBillbag(subscriber);
    }


    ResponseResultListener callback_getbillbag = new ResponseResultListener<BillbagResponse>() {
        @Override
        public void success(BillbagResponse returnMsg) {
            if (returnMsg.getPayments().size() > 0){
                BillbagModel billbagModel = returnMsg.getPayments().get(0);
                billbagModel.setClick(true);
            }
            walletTitleAdapter.clear();
            billBagReturnMsg = returnMsg;
            if (getView() != null){
                setView();
            }
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };


}
