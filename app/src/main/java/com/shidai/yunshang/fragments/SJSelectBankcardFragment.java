package com.shidai.yunshang.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shidai.yunshang.R;
import com.shidai.yunshang.adapters.SelectBankCardAdapter;
import com.shidai.yunshang.constants.Constant;
import com.shidai.yunshang.fragments.base.BasePullRecyclerFragment;
import com.shidai.yunshang.intefaces.BankFragmentRefresh;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.intefaces.SelectBankListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.models.BankmsgModel;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.BankmsgResponse;
import com.shidai.yunshang.networks.responses.SelectCardResponse;
import com.shidai.yunshang.view.widget.NavBarBack;
import com.shidai.yunshang.view.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 创建时间： 2017/7/22.
 * 作者：黄如辉
 * 功能描述：升级的选择银行卡
 */
@EFragment(R.layout.fragment_select_bkancard)
public class SJSelectBankcardFragment extends BasePullRecyclerFragment {

    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;

    private SelectBankCardAdapter adapter_brankcard;
    private List<Object> listmodel = new ArrayList<>();
    private int CURTURNPAGE = Constant.DEFAULTPAGE;
    private double upgradeMoney = 0;
    private int gradeid;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        upgradeMoney = getArguments().getDouble("upgradeMoney");
        gradeid = getArguments().getInt("gradeid");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView(PullRecyclerView recyclerView) {
        mNavbar.setBarTitle("选择支付卡");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });

        getBankMsg();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter_brankcard = new SelectBankCardAdapter(getActivity(), listmodel,
                "+ 添加另一张信用卡", selectBankListener, true));
    }

    /*行点击事件*/
    SelectBankListener selectBankListener = new SelectBankListener<BankmsgModel>() {
        @Override
        public void addBankListener(int bankType) {
            if (bankType == 0){
                //信用卡
                showFragment(getActivity(), BindCreditFragment_.builder().build());
            }else{
                //银行卡
                showFragment(getActivity(), BindDebitFragment_.builder().build());
            }
        }

        @Override
        public void setOnclickListener(BankmsgModel model, int position) {
            //获取银行卡
            getUpgradeSelect(model.getId());
        }
    };

    @Override
    protected void onRefresh(RecyclerView recyclerView) {
        CURTURNPAGE = Constant.DEFAULTPAGE;
        listmodel.clear();
        getBankMsg();
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        CURTURNPAGE++;
        getBankMsg();
    }


    private void getBankMsg() {
        Subscriber subscriber = new PosetSubscriber<BankmsgResponse>().getSubscriber(callback_bank);
        UserManager.getbankmsg(1, CURTURNPAGE, subscriber);
    }

    ResponseResultListener callback_bank = new ResponseResultListener<BankmsgResponse>() {
        @Override
        public void success(BankmsgResponse returnMsg) {
            if (returnMsg.getTotal_pages() < CURTURNPAGE){
                finishLoad(false);
            }else{
                finishLoad(true);
            }
            closeProgress();

            String bottomDes = "+ 添加另一张信用卡";
            listmodel.addAll(returnMsg.getRows());
            adapter_brankcard.setData(listmodel, bottomDes);
        }

        @Override
        public void fialed(String resCode, String message) {
            finishLoad(false);
            closeProgress();
        }
    };

    ResponseResultListener callback_selectbank = new ResponseResultListener<SelectCardResponse>() {
        @Override
        public void success(SelectCardResponse returnMsg) {
            closeProgress();
            showFragment(getActivity(), UpgradeCodeSureFragment_.builder().build());
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

    private void getUpgradeSelect(int id) {
        Subscriber subscriber = new PosetSubscriber<SelectCardResponse>().getSubscriber(callback_selectbank);
        UserManager.upgradeSelectCard(gradeid, id, upgradeMoney, subscriber);
    }

    @Subscribe
    public void refresh(BankFragmentRefresh refresh){
        if (refresh.isfresh){
            CURTURNPAGE = Constant.DEFAULTPAGE;
            listmodel.clear();
            getBankMsg();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
