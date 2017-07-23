package com.shidai.yunshang.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.adapters.MineMsgAdapter;
import com.shidai.yunshang.fragments.base.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/7/23.
 * 作者：黄如辉
 * 功能描述：
 */
@EFragment(R.layout.fragment_minemsg)
public class MineMsgFragment extends BaseFragment {

    @ViewById(R.id.mRecycleView)
    RecyclerView mRecycleView;

    MineMsgAdapter adapter_msg;

    @AfterViews
    void initVeiw(){
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setAdapter(adapter_msg = new MineMsgAdapter());
    }

}
