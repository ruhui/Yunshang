package com.shidai.yunshang.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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
        adapter_surepay.addAll(orderInfo.getChannel());
    }


    AdapterListener adapterListener = new AdapterListener<ChannelModel>() {
        @Override
        public void setItemClickListener(ChannelModel suerPayModel, int position) {
            selectChannel(suerPayModel.getPay_channel(), suerPayModel.getSettle_type());
        }
    };

    private void selectChannel(int pay_channel, String settle_type) {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_selectchannel);
        UserManager.selectChannel(String.valueOf(pay_channel), settle_type, subscriber);
    }

    ResponseResultListener callback_selectchannel = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            closeProgress();
            if (returnMsg){
                if (orderType.equals("UNIONPAY")){
                    //选择银行卡
                    showFragment(getActivity(), SelectBankcardFragment_.builder().build());
                }else if (orderType.equals("ALIPAY")){
                    //扫码支付

                }else if (orderType.equals("WXPAY")){
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
}
