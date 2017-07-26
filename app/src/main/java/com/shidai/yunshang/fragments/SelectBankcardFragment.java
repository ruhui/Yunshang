package com.shidai.yunshang.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.adapters.SelectBankCardAdapter;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间： 2017/7/22.
 * 作者：黄如辉
 * 功能描述：选择银行卡
 */
@EFragment(R.layout.fragment_select_bkancard)
public class SelectBankcardFragment extends BaseFragment {

    @ViewById(R.id.mRecycleView)
    RecyclerView mRecycleView;
    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;
    @ViewById(R.id.txtAddCard)
    TextView txtAddCard;

    private SelectBankCardAdapter adapter_brankcard;
//    private List<SelectBankCardModel> listmodel = new ArrayList<>();

    @AfterViews
    void initView(){
        mNavbar.setBarTitle("选择银行卡");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });


        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mRecycleView.setAdapter(adapter_brankcard = new SelectBankCardAdapter());
//        mRecycleView.setNestedScrollingEnabled(false);
//        adapter_brankcard.addAll(listmodel);

        /*添加银行卡*/
        txtAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(getActivity(), MyBankCardFragment_.builder().build());
            }
        });
    }



}
