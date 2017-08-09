package com.shidai.yunshang.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.AcitivtyFinishListener;
import com.shidai.yunshang.intefaces.ActivityFinish;
import com.shidai.yunshang.managers.DataCleanManager;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.utils.SecurePreferences;
import com.shidai.yunshang.utils.Tool;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

/**
 * 创建时间： 2017/8/10.
 * 作者：黄如辉
 * 功能描述：更多
 */
@EFragment(R.layout.fragment_more)
public class MoreFragment extends BaseFragment{

    @ViewById(R.id.textView21)
    TextView txtMore;
    @ViewById(R.id.textView26)
    TextView txtCach;

    @AfterViews
    void initView(){
        try{
            String totalcash = DataCleanManager.getTotalCacheSize(getActivity());
            txtCach.setText(totalcash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtMore.setText(getString(R.string.version_name, Tool.getAppVersion(getActivity(), false)));
    }

    /*缓存清除*/
    @Click(R.id.relaClearCach)
    void clearCach(){
        showProgress();
        new ClearCachAsynTask().execute("");
    }

    /*关于我们*/
    @Click(R.id.relaAboutus)
    void aboutUs(){

    }

    /*当前版本*/
    @Click(R.id.relaCuturnVersion)
    void curturnVersion(){

    }

    /*服务协议*/
    @Click(R.id.relaXieyi)
    void xieyi(){

    }

    /*退出登录*/
    @Click(R.id.button2)
    void exit(){
        EventBus.getDefault().post(new ActivityFinish(true));
    }

    class ClearCachAsynTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            DataCleanManager.clearAllCache(getActivity());
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            closeProgress();
            try{
                String totalcash = DataCleanManager.getTotalCacheSize(getActivity());
                txtCach.setText(totalcash);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

