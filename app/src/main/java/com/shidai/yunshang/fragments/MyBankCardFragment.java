package com.shidai.yunshang.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.adapters.SelectBankCardAdapter;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.models.SelectBankCardModel;
import com.shidai.yunshang.view.widget.NavBarBack;
import com.shidai.yunshang.view.widget.NavBarSwitch;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间： 2017/7/22.
 * 作者：黄如辉
 * 功能描述：我的银行卡
 */
@EFragment(R.layout.fragment_mybankcrd)
public class MyBankCardFragment extends BaseFragment {

    @ViewById(R.id.mNavbar)
    NavBarSwitch mNavbar;
    @ViewById(R.id.mRecycleView)
    RecyclerView mRecycleView;
    @ViewById(R.id.txtAddCard)
    TextView txtAddCard;

    private SelectBankCardAdapter adapter_brankcard;
    private List<SelectBankCardModel> listmodel = new ArrayList<>();
    private boolean switchBank = false;


    @AfterViews
    void initView(){
        mNavbar.setOnMenuClickListener(new NavBarSwitch.OnMenuClickListener() {

            @Override
            public void onBackClick(View view) {
                super.onBackClick(view);
                finishFragment();
            }

            @Override
            public void onXyMenuClick(View view) {
                super.onXyMenuClick(view);
                switchBank = false;
                initData(6);
                txtAddCard.setText("+ 添加另一张信用卡");
            }

            @Override
            public void onBankMenuClick(View view) {
                super.onBankMenuClick(view);
                switchBank = true;
                initData(10);
                txtAddCard.setText("+ 添加另一张银行卡");
            }
        });

        txtAddCard.setText("+ 添加另一张信用卡");

        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setAdapter(adapter_brankcard = new SelectBankCardAdapter());
        mRecycleView.setNestedScrollingEnabled(false);

        initData(6);

        /*添加银行卡*/
        txtAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchBank){
                    //添加银行卡
                }else{
                    //添加信用卡
                }
            }
        });
    }

    private void initData(int size) {
        listmodel.clear();
        for (int i = 0; i<=size; i++){
            SelectBankCardModel model = new SelectBankCardModel("", "", "**** **** **** 5567");
            listmodel.add(model);
        }
        adapter_brankcard.clear();
        adapter_brankcard.addAll(listmodel);
    }
}
