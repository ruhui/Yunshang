package com.shidai.yunshang.activities.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.shidai.yunshang.R;
import com.shidai.yunshang.activities.LoginActivity_;
import com.shidai.yunshang.activities.MainActivity;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.ActivityFinish;
import com.shidai.yunshang.intefaces.RefreshListener;
import com.shidai.yunshang.view.widget.ProgressDialog;
import com.shidai.yunshang.view.widget.ProgressDialog_;
import com.umeng.analytics.MobclickAgent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
    * 基类
    * @author ruhui
    * @time 2017/2/21 18:05
    *   mProgressDlg进度条     mErrorPage错误界面
   **/

public abstract class BaseActivity extends AppCompatActivity  {


    private static final int REQUEST_CODE = 0; // 请求码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void refreshView(RefreshListener refreshListener){

    }

    @Override
    protected void onResume() {
        /**
         * 设置为横屏
         */
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private boolean isLoadingShown;
    private ProgressDialog mProgressDlg;

    boolean displayLoadingPage(int layoutId, View view) {
        ViewGroup layout = null;
        if (getView() != null) {
            int index = -1;
            if (layoutId != View.NO_ID) {
                layout = (ViewGroup) getView().findViewById(layoutId);
                //TODO
                if (layout != null && layout instanceof View && !(layout instanceof ViewGroup)) {
                    layout = (ViewGroup) layout.getParent();
                    if (layout instanceof LinearLayout) {
                        index = 0;
                    }
                }
            } else {
                layout = ((ViewGroup) this.getView());
            }
            if (layout != null) {
                layout.addView(view, index, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
            }
        }
        return layout != null;
    }

    public BaseActivity getActivity() {
        return this;
    }

    public View getView() {
        return getWindow().getDecorView();
    }

    private boolean displayView(View view, int layoutId) {
        ViewGroup layout = null;
        if (view != null) {
            int index = -1;
            if (layoutId != View.NO_ID) {
                layout = (ViewGroup) findViewById(layoutId);
            } else {
                layout = (ViewGroup) getWindow().getDecorView();
            }
            if (layout != null) {
                if (layout instanceof LinearLayout) {
                    index = 0;
                }
                layout.addView(view, index, new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
        }
        return layout != null;
    }

    ProgressDialog mProgressDialog;

    protected ProgressDialog showProgress() {
        return showProgress((ViewGroup) getView());
    }

    protected ProgressDialog showProgress(ViewGroup viewParent) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog_.build(getActivity());
            mProgressDialog.addToView(viewParent);
        }
        mProgressDialog.show();
        return mProgressDialog;
    }

    protected void closeProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }
    }


    public void showProgress(@StringRes int msgRes) {
        ProgressDialog dialog = showProgress();
        dialog.setTipMsg(msgRes);
    }

    public void showProgress(String msg) {
        ProgressDialog dialog = showProgress();
        dialog.setTipMsg(msg);
    }

    public boolean progressViewIsShowing() {
        if (mProgressDlg != null) {
            return mProgressDlg.isShowing();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeProgress();
    }

    public void showFragment(BaseFragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(0, 0, 0, 0);
        transaction.add(R.id.container, fragment, fragment.getClass().getName());
        fragment.setmLastStackName("" + System.currentTimeMillis() + hashCode());
        transaction.addToBackStack(fragment.getmLastStackName());
        transaction.commitAllowingStateLoss();
    }


    @Subscribe
    public void finishPage(ActivityFinish activityFinish){
        if (activityFinish.isfinish){
            if (BaseActivity.this instanceof MainActivity){
                Intent intent = new Intent(this, LoginActivity_.class);
                startActivity(intent);
            }
            finish();
        }
    }
}
