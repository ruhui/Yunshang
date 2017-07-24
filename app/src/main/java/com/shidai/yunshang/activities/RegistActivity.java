package com.shidai.yunshang.activities;

import com.shidai.yunshang.R;
import com.shidai.yunshang.activities.base.BaseActivity;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/7/24.
 * 作者：黄如辉
 * 功能描述：注册
 */
@EActivity(R.layout.activity_regist)
public class RegistActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;

    @AfterViews
    void initView(){
        mNavbar.setDisplayLeftMenu(false);
        mNavbar.setMiddleTitle("注册");
    }


    @Click(R.id.txtRegist)
    void login(){
        finish();
    }

}
