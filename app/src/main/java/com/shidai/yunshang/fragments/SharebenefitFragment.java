package com.shidai.yunshang.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.adapters.BenefitAdapter;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.BillprofitResponse;
import com.shidai.yunshang.view.widget.NavBarWallet;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import rx.Subscriber;

/**
 * 创建时间： 2017/7/29.
 * 作者：黄如辉
 * 功能描述：分润
 */
@EFragment(R.layout.fragment_sharebenefit)
public class SharebenefitFragment extends BaseFragment {

    @ViewById(R.id.mNavbar)
    NavBarWallet mNavbar;
    @ViewById(R.id.textView36)
    TextView txtShareMoney;
    /*交易笔数*/
    @ViewById(R.id.textView38)
    TextView txtBishu;
    /*交易流水*/
    @ViewById(R.id.textView39)
    TextView txtLiushui;
    /*新增用户*/
    @ViewById(R.id.textView40)
    TextView txtAdduser;
    /*当前排行*/
    @ViewById(R.id.textView41)
    TextView txtPaihang;
    @ViewById(R.id.mRecycleView)
    RecyclerView mRecycleView;

    private BenefitAdapter adapter_benefit;
    private double totalmoney = 0 , mouthbenefit = 0;


    @AfterViews
    void initView(){
        mNavbar.setMiddleTitle("收益");
        mNavbar.setTxtRightView("");mNavbar.setRightVisable(false);
        mNavbar.setOnMenuClickListener(new NavBarWallet.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                //账单
            }

            @Override
            public void onRightMenuClick(View view) {
                super.onRightMenuClick(view);
                //财富宝典
            }
        });

        /*获取收益*/
        getBillprofit();

        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setAdapter(adapter_benefit = new BenefitAdapter());
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
             /*获取收益*/
            getBillprofit();
        }
    }

    private void getBillprofit() {
        Subscriber subscriber = new PosetSubscriber<BillprofitResponse>().getSubscriber(callback_billprofit);
        UserManager.getBillprofit(subscriber);
    }


    /*排行详情*/
    @Click(R.id.relaPaihang)
    void paihangClic(){
        PaihangbangFragment fragment = PaihangbangFragment_.builder().build();
        Bundle bundle = new Bundle();
        //当月分润
        bundle.putDouble("mouthBenefit", mouthbenefit);
        fragment.setArguments(bundle);
        showFragment(getActivity(), fragment);
    }

    /*结算*/
    @Click(R.id.button2)
    void jiesuan(){
        JiesuanFragment fragment = JiesuanFragment_.builder().build();
        Bundle bundle = new Bundle();
        bundle.putDouble("totalMoney", totalmoney);
        fragment.setArguments(bundle);
        showFragment(getActivity(), fragment);
    }


    ResponseResultListener callback_billprofit = new ResponseResultListener<BillprofitResponse>() {
        @Override
        public void success(BillprofitResponse returnMsg) {
            totalmoney = returnMsg.getProfit();
            mouthbenefit = returnMsg.getMonth_profit();
            if (getView() == null){
                return;
            }
            txtShareMoney.setText("¥ "+returnMsg.getProfit());
            txtBishu.setText(""+returnMsg.getDay_trade_count());
            txtLiushui.setText(returnMsg.getDay_trade_amt()+"");
            txtAdduser.setText(returnMsg.getDay_user_count()+"");

            String paihang = "当前排行第 "+returnMsg.getSort()+" 名";

            SpannableStringBuilder builder = new SpannableStringBuilder(paihang);
            //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
            builder.setSpan(colorSpan, 5, 5+String.valueOf(returnMsg.getSort()).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            txtPaihang.setText(builder);
            adapter_benefit.clear();
            adapter_benefit.addAll(returnMsg.getGrades());
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };
}
