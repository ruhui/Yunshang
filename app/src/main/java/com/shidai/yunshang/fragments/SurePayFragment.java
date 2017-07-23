package com.shidai.yunshang.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shidai.yunshang.adapters.SurePayAdapter;
import com.shidai.yunshang.intefaces.AdapterListener;
import com.shidai.yunshang.models.SuerPayModel;
import com.shidai.yunshang.R;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.view.widget.FullyLinearLayoutManager;
import com.shidai.yunshang.view.widget.NavBarBack;
import com.shidai.yunshang.view.widget.SpaceItemDecoration;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

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

    private String orderId = "", orderType = "", orderMoney = "";
    private List<SuerPayModel> listModel = new ArrayList<>();
    private SurePayAdapter adapter_surepay;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        orderId = getArguments().getString("orderId");
        orderType = getArguments().getString("orderType");
        orderMoney = getArguments().getString("orderMoney");
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

        initData();

        txtOrderId.setText(orderId);
        txtType.setText(orderType);
        txtMoney.setText(orderMoney);

        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(getActivity());
        manager.setSmoothScrollbarEnabled(true);
        mRecycleView.setLayoutManager(manager);
        mRecycleView.setAdapter(adapter_surepay = new SurePayAdapter(adapterListener));
        adapter_surepay.addAll(listModel);
    }

    private void initData() {
        for (int i=0;i<6;i++){
            SuerPayModel model = new SuerPayModel("", "银联小额，秒到 费率0.38% 结算费用2.0","单笔5000  单日200000");
            listModel.add(model);
        }
    }


    AdapterListener adapterListener = new AdapterListener<SuerPayModel>() {
        @Override
        public void setItemClickListener(SuerPayModel suerPayModel, int position) {
            showFragment(getActivity(), SelectBankcardFragment_.builder().build());
        }
    };
}
