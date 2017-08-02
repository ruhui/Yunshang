package com.shidai.yunshang.fragments;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.shidai.yunshang.R;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.utils.ToastUtil;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.NavBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/8/2.
 * 作者：黄如辉
 * 功能描述：推荐人
 */
@EFragment(R.layout.fragment_recommend)
public class RecommendFragment extends BaseFragment {

    @ViewById(R.id.mNavbar)
    NavBar mNavbar;
    @ViewById(R.id.edtPhone)
    EditText edtPhone;

    @AfterViews
    void initView(){
        mNavbar.setMiddleTitle("推荐人");
        mNavbar.setOnMenuClickListener(new NavBar.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });
    }



    @Click(R.id.button2)
    void submitData(){
        String phoneNum = edtPhone.getText().toString();
        if (TextUtils.isEmpty(phoneNum) || !Tool.checkPhoneNum(phoneNum)){
            ToastUtil.showToast("请输入正确的手机号码");
            return;
        }


    }
}
