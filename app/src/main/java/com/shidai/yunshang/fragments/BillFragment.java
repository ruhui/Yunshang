package com.shidai.yunshang.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shidai.yunshang.R;
import com.shidai.yunshang.adapters.BillListAdapter;
import com.shidai.yunshang.constants.Constant;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.fragments.base.BasePullRecyclerFragment;
import com.shidai.yunshang.intefaces.BillAdapterListener;
import com.shidai.yunshang.intefaces.EnumBillType;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.models.BillModel;
import com.shidai.yunshang.models.BillTitleModel;
import com.shidai.yunshang.models.BollParentModel;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.BillListResponse;
import com.shidai.yunshang.view.widget.NavBarBill;
import com.shidai.yunshang.view.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 描述：账单
 * 创建作者： 黄如辉
 * 创建时间： 2017/8/9 9:56
 **/
@EFragment(R.layout.fragment_bill)
public class BillFragment extends BasePullRecyclerFragment {

    @ViewById(R.id.mNavbar)
    NavBarBill mNavbar;


    /*类型*/
    private EnumBillType billType = EnumBillType.TYPE_SK;
    private int CURTURNPAGE = Constant.DEFAULTPAGE;
    private List<Object> listObject = new ArrayList<>();
    private BillListAdapter billListAdapter;

    @Override
    protected void initView(PullRecyclerView recyclerView) {
        getBillList();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(billListAdapter = new BillListAdapter(getActivity(), listObject, billAdapterListener));

        mNavbar.setOnMenuClickListener(new NavBarBill.OnMenuClickListener() {
            @Override
            public void onBackClick(View view) {
                super.onBackClick(view);
                finishFragment();
            }

            @Override
            public void onSKMenuClick(View view) {
                super.onSKMenuClick(view);
                billType = EnumBillType.TYPE_SK;
                CURTURNPAGE = Constant.DEFAULTPAGE;
                listObject.clear();
                getBillList();
            }

            @Override
            public void onFRMenuClick(View view) {
                super.onFRMenuClick(view);
                billType = EnumBillType.TYPE_FR;
                CURTURNPAGE = Constant.DEFAULTPAGE;
                listObject.clear();
                getBillList();
            }

            @Override
            public void onTXMenuClick(View view) {
                super.onTXMenuClick(view);
                billType = EnumBillType.TYPE_TX;
                CURTURNPAGE = Constant.DEFAULTPAGE;
                listObject.clear();
                getBillList();
            }
        });
    }


    BillAdapterListener billAdapterListener = new BillAdapterListener() {
        @Override
        public void setOnTitleClickListener(BillTitleModel model, int position) {
            switch (billType) {
                case TYPE_SK:
                    //收款

                    break;
                case TYPE_DDSR:
                    //订单收入
                    break;
                case TYPE_TK:
                    //退款
                    break;
                case TYPE_TX:
                    //提现
                    TXDetailFragment fragment = TXDetailFragment_.builder().build();
                    Bundle bundle = new Bundle();
                    bundle.putString("orderno", model.getOrder_no());
                    fragment.setArguments(bundle);
                    showFragment(getActivity(), fragment);
                    break;
                case TYPE_FR:
                    //分润
                    break;
                case TYPE_SXF:
                    //手续费
                    break;
                case TYPE_ZF:
                    //支付
                    break;
            }
        }

        @Override
        public void setOnItemClickListener(BillModel model, int position) {
            switch (billType) {
                case TYPE_SK:
                    //收款

                    break;
                case TYPE_DDSR:
                    //订单收入
                    break;
                case TYPE_TK:
                    //退款
                    break;
                case TYPE_TX:
                    //提现
                    TXDetailFragment fragment = TXDetailFragment_.builder().build();
                    Bundle bundle = new Bundle();
                    bundle.putString("orderno", model.getOrder_no());
                    fragment.setArguments(bundle);
                    showFragment(getActivity(), fragment);
                    break;
                case TYPE_FR:
                    //分润
                    break;
                case TYPE_SXF:
                    //手续费
                    break;
                case TYPE_ZF:
                    //支付
                    break;
            }
        }
    };


    @Override
    protected void onRefresh(RecyclerView recyclerView) {
        CURTURNPAGE = Constant.DEFAULTPAGE;
        listObject.clear();
        getBillList();
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        CURTURNPAGE++;
        getBillList();
    }


    /*获取账单列表*/
    private void getBillList() {
        Subscriber subscriber = new PosetSubscriber<BillListResponse>().getSubscriber(callback_billlist);
        UserManager.getBillList("", billType, "", "", "", CURTURNPAGE, subscriber);
    }

    ResponseResultListener callback_billlist = new ResponseResultListener<BillListResponse>() {
        @Override
        public void success(BillListResponse returnMsg) {
            if (returnMsg.getTotal_pages() < CURTURNPAGE){
                finishLoad(false);
            }else{
                finishLoad(true);
            }
            for (BollParentModel billParent : returnMsg.getRows()){
                String orderno = "";
                if (billParent.getItems().size() > 0){
                    orderno = billParent.getItems().get(0).getOrder_no();
                }
                BillTitleModel billTitleModel = new BillTitleModel(orderno, billParent.getDate(), billParent.getTotal());
                listObject.add(billTitleModel);
                listObject.addAll(billParent.getItems());
            }

            billListAdapter.setData(billType, listObject);
        }

        @Override
        public void fialed(String resCode, String message) {
            finishLoad(true);
        }
    };

}
