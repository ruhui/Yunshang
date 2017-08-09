package com.shidai.yunshang.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shidai.yunshang.R;
import com.shidai.yunshang.activities.InputMoneyActivity;
import com.shidai.yunshang.activities.InputMoneyActivity_;
import com.shidai.yunshang.activities.TixianWalletActivity_;
import com.shidai.yunshang.activities.WebActivity_;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UrlAddressManger;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.models.BillbagModel;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.BillbagResponse;
import com.shidai.yunshang.networks.responses.BillprofitResponse;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.NavBar;
import com.shidai.yunshang.view.widget.PicTextView45;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import rx.Subscriber;

/**
 * 创建时间： 2017/7/18.
 * 作者：黄如辉
 * 功能描述：
 */

@EFragment(R.layout.fragment_home)
public class HomeFragment extends BaseFragment {

    @ViewById(R.id.mNavbar)
    NavBar mNavbar;
    @ViewById(R.id.picText1)
    PicTextView45 picText1;
    @ViewById(R.id.picText2)
    PicTextView45 picText2;
    @ViewById(R.id.picText3)
    PicTextView45 picText3;
    @ViewById(R.id.picText4)
    PicTextView45 picText4;

    @ViewById(R.id.picText5)
    PicTextView45 picText5;
    @ViewById(R.id.picText6)
    PicTextView45 picText6;
    @ViewById(R.id.picText7)
    PicTextView45 picText7;
    @ViewById(R.id.picText8)
    PicTextView45 picText8;
    @ViewById(R.id.imgVidio)
    ImageView imgVidio;

    private BillbagResponse billBagReturnMsg;
    private double mouthbenefit = 0;

    @AfterViews
    void initView(){
        mNavbar.setMiddleText(R.string.home_tabtitle);
        picText1.setTextView(R.string.home_sy_qywsc);picText1.setImageResource(R.drawable.sy_qywsc);
        picText2.setTextView(R.string.home_sy_zbsc);picText2.setImageResource(R.drawable.sy_zbsc);
        picText3.setTextView(R.string.home_sy_wyfs);picText3.setImageResource(R.drawable.sy_wyfs);
        picText4.setTextView(R.string.home_sy_zqjy);picText4.setImageResource(R.drawable.sy_zqjy);

        picText5.setTextView(R.string.home_sy_wysj);picText5.setImageResource(R.drawable.sy_wysj);
        picText6.setTextView(R.string.home_sy_syph);picText6.setImageResource(R.drawable.sy_syph);
        picText7.setTextView(R.string.home_sy_wdkf);picText7.setImageResource(R.drawable.sy_wdkf);
        picText8.setTextView(R.string.home_sy_gd);picText8.setImageResource(R.drawable.sy_ewm);

        /*微商城*/
        picText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebActivity_.class);
                intent.putExtra("titleBar", getResources().getString(R.string.home_sy_qywsc));
                intent.putExtra("webUrl", UrlAddressManger.HOMEVSHOPADDRESS);
                startActivity(intent);
            }
        });

        /*珠宝*/
        picText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebActivity_.class);
                intent.putExtra("titleBar", getResources().getString(R.string.home_sy_zbsc));
                intent.putExtra("webUrl", UrlAddressManger.HOMEJEWELLERYADDRESS);
                startActivity(intent);
            }
        });

        /*视频*/
        imgVidio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebActivity_.class);
                intent.putExtra("titleBar", "视频推广");
                intent.putExtra("webUrl", UrlAddressManger.HOMEVIEWHELP);
                startActivity(intent);
            }
        });

        /*证券交易*/
        picText4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebActivity_.class);
                intent.putExtra("titleBar", getResources().getString(R.string.home_sy_zqjy));
                intent.putExtra("webUrl", UrlAddressManger.HOMEDEALTRADE);
                startActivity(intent);
            }
        });

        /*无极服饰*/
        picText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebActivity_.class);
                intent.putExtra("titleBar", getResources().getString(R.string.home_sy_wyfs));
                intent.putExtra("webUrl", UrlAddressManger.HOMECLOTHADDRESS);
                startActivity(intent);
            }
        });

        /*我要升级*/
        picText5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(getActivity(), AuthorizationFragment_.builder().build());
            }
        });

        /*收益排行*/
        picText6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaihangbangFragment fragment = PaihangbangFragment_.builder().build();
                Bundle bundle = new Bundle();
                //当月分润
                bundle.putDouble("mouthBenefit", mouthbenefit);
                fragment.setArguments(bundle);
                showFragment(getActivity(), fragment);
            }
        });

        /*我的客服*/
        picText7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebActivity_.class);
                intent.putExtra("titleBar", "客服");
                intent.putExtra("webUrl", UrlAddressManger.CUSTOMSERVICE);
                startActivity(intent);
            }
        });

        /*我的二维码*/
        picText8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(getActivity(), ErweimaFragment_.builder().build());
            }
        });

        /*账单*/
        mNavbar.setOnMenuClickListener(new NavBar.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                showFragment(getActivity(), BillFragment_.builder().build());
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            getBillbag();
            getBillprofit();
        }
    }

    /*银联支付*/
    @Click(R.id.relaYinlian)
    void payYinlian(){
        //ALIPAY，WXPAY，UNIONPAY，GATEWAY
        Intent intent = new Intent(getActivity(), InputMoneyActivity_.class);
        intent.putExtra("navbarTitle", "银联支付");
        intent.putExtra("payCode", "UNIONPAY");
        startActivity(intent);

//        InputMoneyFragment fragment = InputMoneyFragment_.builder().build();
//        Bundle bundle = new Bundle();
//        bundle.putString("navbarTitle", "银联支付");
//        bundle.putString("payCode", "UNIONPAY");
//        fragment.setArguments(bundle);
//        showFragment(getActivity(), fragment);
    }

    /*扫码*/
    @Click(R.id.relaSaoma)
    void paySaoma(){

        Intent intent = new Intent(getActivity(), InputMoneyActivity_.class);
        intent.putExtra("navbarTitle", "扫码支付");
        intent.putExtra("payCode", "WXPAY");
        startActivity(intent);

//        InputMoneyFragment fragment = InputMoneyFragment_.builder().build();
//        Bundle bundle = new Bundle();
//        bundle.putString("navbarTitle", "扫码支付");
//        bundle.putString("payCode", "GATEWAY");
//        fragment.setArguments(bundle);
//        showFragment(getActivity(), fragment);
    }

    /*提现*/
    @Click(R.id.relaTixian)
    void payTixian(){

        Intent intent = new Intent(getActivity(), TixianWalletActivity_.class);
        intent.putExtra("tixianMoney", Tool.formatPrice(billBagReturnMsg.getDeposit()));
        startActivity(intent);

//        InputMoneyFragment fragment = InputMoneyFragment_.builder().build();
//        Bundle bundle = new Bundle();
//        bundle.putString("navbarTitle", "提现");
//        fragment.setArguments(bundle);
//        showFragment(getActivity(), fragment);
    }

    private void getBillbag() {
        Subscriber subscriber = new PosetSubscriber<BillbagResponse>().getSubscriber(callback_getbillbag);
        UserManager.getBillbag(subscriber);
    }

    private void getBillprofit() {
        Subscriber subscriber = new PosetSubscriber<BillprofitResponse>().getSubscriber(callback_billprofit);
        UserManager.getBillprofit(subscriber);
    }


    ResponseResultListener callback_getbillbag = new ResponseResultListener<BillbagResponse>() {
        @Override
        public void success(BillbagResponse returnMsg) {
            billBagReturnMsg = returnMsg;
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };


    ResponseResultListener callback_billprofit = new ResponseResultListener<BillprofitResponse>() {
        @Override
        public void success(BillprofitResponse returnMsg) {
            mouthbenefit = returnMsg.getMonth_profit();
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };
}
