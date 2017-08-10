package com.shidai.yunshang.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.shidai.yunshang.R;
import com.shidai.yunshang.activities.base.BaseActivity;
import com.shidai.yunshang.fragments.base.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import cn.jpush.android.api.JPushInterface;

@EActivity(R.layout.activity_jupsh_receive)
public class JupshReceiveActivity extends BaseActivity {

    @AfterViews
    void initView(){
        Bundle bundle = getIntent().getExtras();
        String extData = bundle.getString(JPushInterface.EXTRA_EXTRA);

    }

    private void showJupshFragment(BaseFragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(0, 0, 0, 0);
        transaction.add(R.id.container, fragment, fragment.getClass().getName());
//        fragment.setmLastStackName("" + System.currentTimeMillis() + hashCode());
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void finishActivity(boolean isfinish) {
        if (isfinish){
            finish();
        }
    }
}
