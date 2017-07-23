package com.shidai.yunshang.fragments;

import android.view.View;

import com.shidai.yunshang.R;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.view.widget.ItemView1;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/7/22.
 * 作者：黄如辉
 * 功能描述：
 */
@EFragment(R.layout.fragment_mine)
public class MineFragment extends BaseFragment {

    @ViewById(R.id.itemView1)
    ItemView1 itemView1;
    @ViewById(R.id.itemView2)
    ItemView1 itemView2;
    @ViewById(R.id.itemView3)
    ItemView1 itemView3;
    @ViewById(R.id.itemView4)
    ItemView1 itemView4;
    @ViewById(R.id.itemView5)
    ItemView1 itemView5;
    @ViewById(R.id.itemView6)
    ItemView1 itemView6;
    @ViewById(R.id.itemView7)
    ItemView1 itemView7;
    @ViewById(R.id.itemView8)
    ItemView1 itemView8;

    @AfterViews
    void initView(){
        itemView1.setLeftIcon(R.drawable.wd_tjr);itemView1.setMiddelTxt("推荐人");
        itemView2.setLeftIcon(R.drawable.wd_smrz);itemView2.setMiddelTxt("实名认证");
        itemView3.setLeftIcon(R.drawable.wd_yhkgl);itemView3.setMiddelTxt("银行卡管理");
        itemView4.setLeftIcon(R.drawable.wd_xxtz);itemView4.setMiddelTxt("消息通知");
        itemView5.setLeftIcon(R.drawable.wd_dd);itemView5.setMiddelTxt("我的订单");
        itemView6.setLeftIcon(R.drawable.wd_shdz);itemView6.setMiddelTxt("收货地址");
        itemView7.setLeftIcon(R.drawable.wd_kf);itemView7.setMiddelTxt("QQ客服");
        itemView8.setLeftIcon(R.drawable.wd_gd);itemView8.setMiddelTxt("更多");itemView8.setLineVisiable(false);

        /*消息通知*/
        itemView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(getActivity(), MineMsgFragment_.builder().build());
            }
        });

        /*银行卡管理*/
        itemView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(getActivity(), MyBankCardFragment_.builder().build());
            }
        });

    }



}
