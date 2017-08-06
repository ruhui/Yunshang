package com.shidai.yunshang.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.adapters.AuthorizaAdapter;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.AdapterListener;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.ShowupResponse;
import com.shidai.yunshang.utils.ImageLoader;
import com.shidai.yunshang.utils.SecurePreferences;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.Serializable;
import java.util.List;

import rx.Subscriber;

/**
 * 创建时间： 2017/7/28.
 * 作者：黄如辉
 * 功能描述：更多授权
 */
@EFragment(R.layout.fragment_authorization)
public class AuthorizationFragment extends BaseFragment {

    @ViewById(R.id.imageView8)
    ImageView imgHeadView;
    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;
    @ViewById(R.id.txtLevel)
    TextView txtLevel;
    @ViewById(R.id.progressBar)
    ProgressBar progressBar;
    @ViewById(R.id.mRecycleView)
    RecyclerView mRecycleView;

    private AuthorizaAdapter adapter_author;

    @AfterViews
    void initView(){
        mNavbar.setMiddleTitle("更多授权");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });


        /*获取授权数据*/
        getAuthorization();

        String userPhoto = SecurePreferences.getInstance().getString("USERPHOTO", "");
        String gradeName = SecurePreferences.getInstance().getString("USERGRADENAME", "");
        int gradeCount = SecurePreferences.getInstance().getInt("USERGRADECOUNT", 0);
        int curturnGrade = SecurePreferences.getInstance().getInt("USERGRADEID", 0);

        ImageLoader.loadCircleImage(Tool.getPicUrl(getActivity(), userPhoto, 48, 48), imgHeadView, R.drawable.dl_tx);
        txtLevel.setText(getResources().getString(R.string.shouquanzizhi) +"  "+ gradeName);
        progressBar.setMax(gradeCount);
        progressBar.setProgress(curturnGrade);

        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setAdapter(adapter_author = new AuthorizaAdapter(adapterListener));
    }


    AdapterListener adapterListener = new AdapterListener<ShowupResponse>() {
        @Override
        public void setItemClickListener(ShowupResponse o, int position) {
            //升级
            UpgradeFragment fragment = UpgradeFragment_.builder().build();
            Bundle bundle = new Bundle();
            bundle.putSerializable("showupResponse", (Serializable) o);
            fragment.setArguments(bundle);
            showFragment(getActivity(), fragment);

        }
    };


    private void getAuthorization() {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<List<ShowupResponse>>().getSubscriber(callback_showup);
        UserManager.getShowup(subscriber);
    }

    ResponseResultListener callback_showup = new ResponseResultListener<List<ShowupResponse>>() {
        @Override
        public void success(List<ShowupResponse> returnMsg) {
            closeProgress();
            adapter_author.addAll(returnMsg);
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

}
