package com.shidai.yunshang.fragments;

import android.content.Context;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.ShoukuanDetailResponse;

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
    /*商品名称*/
    @ViewById(R.id.textView115)
    TextView txtProductName;
    /*单价*/
    @ViewById(R.id.textView118)
    TextView txtProductPrice;
    /*数量*/
    @ViewById(R.id.textView120)
    TextView txtCount;
    /*金额*/
    @ViewById(R.id.textView122)
    TextView txtAmount;

    private String orderno;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        orderno = getArguments().getString("orderno");
    }


    @AfterViews
    void initView(){
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
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };
}
