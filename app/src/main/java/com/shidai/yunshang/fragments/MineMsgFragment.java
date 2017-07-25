package com.shidai.yunshang.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.shidai.yunshang.R;
import com.shidai.yunshang.adapters.MineMsgAdapter;
import com.shidai.yunshang.constants.Constant;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.fragments.base.BasePullRecyclerFragment;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.BulletinResponse;
import com.shidai.yunshang.networks.responses.SystemResponse;
import com.shidai.yunshang.view.widget.NavBarBack;
import com.shidai.yunshang.view.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.CustomTitle;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import rx.Subscriber;

/**
 * 创建时间： 2017/7/23.
 * 作者：黄如辉
 * 功能描述：
 */
@EFragment(R.layout.fragment_minemsg)
public class MineMsgFragment extends BasePullRecyclerFragment {

    @ViewById(R.id.textView19)
    TextView txtContent;
    @ViewById(R.id.textView20)
    TextView txtCount;
    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;

    private int CURTURNPAGE = Constant.DEFAULTPAGE;
    private MineMsgAdapter adapter_msg;

    @Override
    protected void initView(PullRecyclerView recyclerView) {
        mNavbar.setMiddleTitle("消息");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter_msg = new MineMsgAdapter());

        getBulletin();
        /*系统消息*/
        getSystemMsg();
    }

    @Override
    protected void onRefresh(RecyclerView recyclerView) {
        CURTURNPAGE = Constant.DEFAULTPAGE;
        /*系统消息*/
        getSystemMsg();
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        CURTURNPAGE++;
        /*系统消息*/
        getSystemMsg();
    }


    private void getSystemMsg(){
        Subscriber subscriber = new PosetSubscriber<SystemResponse>().getSubscriber(callback_systemmsg);
        UserManager.getsystemmsg(CURTURNPAGE, subscriber);
    }

    private void getBulletin() {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<BulletinResponse>().getSubscriber(callback_bulletin);
        UserManager.getBulletin(subscriber);
    }

    ResponseResultListener callback_bulletin = new ResponseResultListener<BulletinResponse>() {
        @Override
        public void success(BulletinResponse returnMsg) {
            closeProgress();
            txtContent.setText(returnMsg.getTitle());
            if (returnMsg.getNew_count() == 0){
                txtCount.setVisibility(View.GONE);
            }else{
                txtCount.setVisibility(View.VISIBLE);
                txtCount.setText(returnMsg.getNew_count());
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

    ResponseResultListener callback_systemmsg = new ResponseResultListener<SystemResponse>() {
        @Override
        public void success(SystemResponse returnMsg) {
            if (returnMsg.getTotal_pages() < CURTURNPAGE){
                finishLoad(false);
            }else{
                finishLoad(true);
            }

            if (CURTURNPAGE == Constant.DEFAULTPAGE){
                adapter_msg.clear();
            }
            adapter_msg.addAll(returnMsg.getRows());
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };
}
