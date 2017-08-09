package com.shidai.yunshang.fragments;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.TixianDetailResponse;
import com.shidai.yunshang.utils.LogUtil;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import rx.Subscriber;

/**
 * 描述：提现详情
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/9 13:58
 **/
@EFragment(R.layout.fragment_skdetail)
public class TXDetailFragment extends BaseFragment {
    /*状态*/
    @ViewById(R.id.textView98)
    TextView txtStatu;
    /*提现手续费*/
    @ViewById(R.id.textView96)
    TextView txtSXF;
    /*实收*/
    @ViewById(R.id.textView94)
    TextView txtShishou;
    /*支付方式*/
    @ViewById(R.id.textView92)
    TextView payType;
    /*订单号*/
    @ViewById(R.id.textView90)
    TextView txtOrderno;
    /*交易时间*/
    @ViewById(R.id.textView88)
    TextView txtTime;
    /*交易金额*/
    @ViewById(R.id.textView85)
    TextView txtMoney;
    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;

    private String orderno;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        orderno = getArguments().getString("orderno");
    }

    @AfterViews
    void initView(){
        /*获取提现详情*/
        getTixianDetail();
        mNavbar.setMiddleTitle("提现详情");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });
    }

    private void getTixianDetail() {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<TixianDetailResponse>().getSubscriber(callback_tixiandetail);
        UserManager.getTixianDetail(orderno, subscriber);
    }

    ResponseResultListener callback_tixiandetail = new ResponseResultListener<TixianDetailResponse>() {
        @Override
        public void success(TixianDetailResponse returnMsg) {
            closeProgress();
            txtMoney.setText("¥"+returnMsg.getTransfer_amount());
            txtTime.setText(returnMsg.getApply_time());
            txtOrderno.setText(returnMsg.getOrder_no());
            if (returnMsg.getStatus() == 1){
                //1提现申请，2完成，3转账中，4失败
                txtStatu.setText("提现申请");
            }else if (returnMsg.getStatus() == 2){
                txtStatu.setText("完成");
            }else if (returnMsg.getStatus() == 3){
                txtStatu.setText("转账中");
            }else if (returnMsg.getStatus() == 4){
                txtStatu.setText("失败");
            }
            txtShishou.setText("¥"+returnMsg.getAmount());
            txtSXF.setText("¥"+returnMsg.getSettle_amount());
            payType.setText(returnMsg.getPay_name());
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
            LogUtil.E("fialed", "fialed");
        }
    };
}
