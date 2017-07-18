package com.shidai.yunshang.fragments.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shidai.yunshang.R;
import com.shidai.yunshang.view.widget.ProgressDialog;
import com.shidai.yunshang.view.widget.ProgressDialog_;
import com.umeng.analytics.MobclickAgent;
import org.androidannotations.annotations.EFragment;

import java.util.List;

/**
   *
   * @author ruhui
   * @time 2017/2/22 15:34
  **/

@EFragment
public abstract class BaseFragment extends Fragment {

    private static final int REQUEST_CODE = 0; // 请求码
    private String mLastStackName = "";


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onResume() {
       super.onResume();
       MobclickAgent.onPageStart(this.getClass().getName());
   }


   public void onPause() {
       super.onPause();
       MobclickAgent.onPageEnd(this.getClass().getName());
   }

   public boolean onBackPressed() {
       return false;
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

   protected void hideProgress() {
       if (mProgressDialog != null) {
           mProgressDialog.hide();
       }
   }

   protected void closeProgress() {
       if (mProgressDialog != null) {
           mProgressDialog.removeFromView();
           mProgressDialog = null;
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

   public void startActivity(Class cls) {
       Intent intent = new Intent();
       intent.setClass(getActivity(), cls);
       startActivity(intent);
   }

   @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data) {
       List<Fragment> fragments = getChildFragmentManager().getFragments();
       if (fragments != null) {
           for (Fragment fragment : fragments) {
               if (fragment != null){
                   fragment.onActivityResult(requestCode, resultCode, data);
               }
           }
       }
   }

    public String getmLastStackName() {
        return mLastStackName;
    }


    public void setmLastStackName(String mLastStackName) {
       this.mLastStackName = mLastStackName;
   }

   @Override
   public void onDetach() {
       super.onDetach();
   }

   @Override
   public void onViewCreated(View view, Bundle savedInstanceState) {
       super.onViewCreated(view, savedInstanceState);
       view.setClickable(true);
   }

   @Override
   public void onDestroyView() {
       closeProgress();
       super.onDestroyView();

   }

  public void showFragment(Context actovoty, BaseFragment fragment){
      FragmentTransaction transaction = ((AppCompatActivity)actovoty).getSupportFragmentManager().beginTransaction();
      transaction.setCustomAnimations(0, 0, 0, 0);
      transaction.add(R.id.container, fragment, fragment.getClass().getName());
      fragment.mLastStackName = "" + System.currentTimeMillis() + hashCode();
      transaction.addToBackStack(fragment.mLastStackName);
      transaction.commitAllowingStateLoss();
  }

  public void showFragment(Context actovoty, BaseFragment fragment, int fragmentlayoutid){
      FragmentTransaction transaction = ((AppCompatActivity)actovoty).getSupportFragmentManager().beginTransaction();
      transaction.setCustomAnimations(0, 0, 0, 0);
      transaction.add(fragmentlayoutid, fragment, fragment.getClass().getName());
      fragment.mLastStackName = "" + System.currentTimeMillis() + hashCode();
      transaction.addToBackStack(fragment.mLastStackName);
      transaction.commitAllowingStateLoss();
  }

  public void finishFragment(){
      getFragmentManager().popBackStackImmediate(mLastStackName,
              FragmentManager.POP_BACK_STACK_INCLUSIVE);
  }



}
