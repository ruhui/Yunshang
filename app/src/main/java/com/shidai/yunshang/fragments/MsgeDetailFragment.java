package com.shidai.yunshang.fragments;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/8/5.
 * 作者：黄如辉
 * 功能描述：
 */
@EFragment(R.layout.fragment_msgdetail)
public class MsgeDetailFragment extends BaseFragment {

    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;

    @ViewById(R.id.textView25)
    TextView txtTitle;
    @ViewById(R.id.textView26)
    TextView txtTime;
    @ViewById(R.id.textView27)
    TextView txtContent;

    private String title = "", content = "", creattime = "";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        title = getArguments().getString("title");
        content = getArguments().getString("content");
        creattime = getArguments().getString("creattime");
    }

    @AfterViews
    void initView(){
        mNavbar.setMiddleTitle("信息详情");
        txtTitle.setText(title);txtTime.setText(creattime);txtContent.setText(content);
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });
    }
}
