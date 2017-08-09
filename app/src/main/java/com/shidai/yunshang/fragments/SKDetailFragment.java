package com.shidai.yunshang.fragments;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.ShoukuanDetailResponse;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import rx.Subscriber;

/**
 * 描述：收款详情
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/9 14:40
 **/
@EFragment(R.layout.fragment_billskdetail)
public class SKDetailFragment extends BaseFragment {
    /*交易金额*/
    @ViewById(R.id.textView99)
    TextView txtMoney;
    /*交易时间*/
    @ViewById(R.id.textView102)
    TextView txtTime;
    /*订单号*/
    @ViewById(R.id.textView104)
    TextView txtOrderno;
    /*支付方式*/
    @ViewById(R.id.textView106)
    TextView txtPaytype;
    /*合计金额*/
    @ViewById(R.id.textView108)
    TextView txtTotalMoney;
    /*应收*/
    @ViewById(R.id.textView110)
    TextView txtYS;
    /*手续费*/
    @ViewById(R.id.textView112)
    TextView txtSXF;
    /*实收*/
    @ViewById(R.id.textView114)
    TextView txtSH;
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
        mNavbar.setMiddleTitle("收款详情");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });
        /*获取收款详情*/
        getReceiptDetail();
    }

    private void getReceiptDetail() {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<ShoukuanDetailResponse>().getSubscriber(callback_receipt);
        UserManager.getReceiptDetail(orderno, subscriber);
    }


    ResponseResultListener callback_receipt = new ResponseResultListener<ShoukuanDetailResponse>() {
        @Override
        public void success(ShoukuanDetailResponse returnMsg) {
            closeProgress();
            txtMoney.setText("¥"+returnMsg.getOrder_amount());
            txtTime.setText(returnMsg.getOrder_time());
            txtOrderno.setText(returnMsg.getOrder_no());
//            String orderStatu = "";
//            if (returnMsg.getStatus() == 1){
//                orderStatu = "待支付";
//            }else if (returnMsg.getStatus() == 2){
//                orderStatu = "已完成";
//            }else if (returnMsg.getStatus() == 3){
//                orderStatu = "支付中";
//            }else if (returnMsg.getStatus() == 4){
//                orderStatu = "失败";
//            }else if (returnMsg.getStatus() == 5){
//                orderStatu = "已关闭";
//            }
            txtPaytype.setText(returnMsg.getPay_name());
            txtTotalMoney.setText("¥"+returnMsg.getOrder_amount());
            txtYS.setText("¥"+returnMsg.getPay_amount());
            txtSXF.setText("¥"+returnMsg.getFee_amount());
            txtSH.setText("¥"+returnMsg.getAmount());
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };
}
