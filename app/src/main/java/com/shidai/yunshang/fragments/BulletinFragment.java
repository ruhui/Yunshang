package com.shidai.yunshang.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shidai.yunshang.R;
import com.shidai.yunshang.adapters.BulletinAdapter;
import com.shidai.yunshang.constants.Constant;
import com.shidai.yunshang.fragments.base.BasePullRecyclerFragment;
import com.shidai.yunshang.intefaces.AdapterListener;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.models.BulletinMode;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.BulletinDataResponst;
import com.shidai.yunshang.networks.responses.BulletinResponse;
import com.shidai.yunshang.view.widget.NavBarBack;
import com.shidai.yunshang.view.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import rx.Subscriber;

/**
 * 创建时间： 2017/8/5.
 * 作者：黄如辉
 * 功能描述：公告列表
 */
@EFragment(R.layout.framgment_bulletin)
public class BulletinFragment extends BasePullRecyclerFragment{

    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;

    private int CURTURNPAGE = Constant.DEFAULTPAGE;
    private BulletinAdapter adapter_bullet;
    private List<BulletinMode> mList = new ArrayList<>();


    @Override
    protected void initView(PullRecyclerView recyclerView) {
        mNavbar.setMiddleTitle("平台公告");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter_bullet = new BulletinAdapter(adapterListener));

        getBulletins();
    }


    AdapterListener adapterListener = new AdapterListener<BulletinMode>(){

        @Override
        public void setItemClickListener(BulletinMode o, int position) {
            /*点击*/
            BulletinMode mode = mList.get(position);
            if (mode.getId() == o.getId()){
                mode.setIs_read(true);
                //设置已读
                List<Integer> list_Id = new ArrayList<>();
                list_Id.add(mode.getId());
                setRead(list_Id);
                adapter_bullet.clear();
                adapter_bullet.addAll(mList);
            }

            /*到详情界面*/
            MsgeDetailFragment fragment = MsgeDetailFragment_.builder().build();
            Bundle bundle = new Bundle();
            bundle.putString("title", o.getTitle());
            bundle.putString("content", o.getContent());
            bundle.putString("creattime", o.getCreate_time());
            fragment.setArguments(bundle);
            showFragment(getActivity(), fragment);
        }
    };

    @Override
    protected void onRefresh(RecyclerView recyclerView) {
        //上拉刷新数据
        CURTURNPAGE = Constant.DEFAULTPAGE;
        mList.clear();
        getBulletins();
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        CURTURNPAGE++;
        getBulletins();
    }


    /*获取消息*/
    private void getBulletins() {
        if (CURTURNPAGE == Constant.DEFAULTPAGE){
            showProgress();
        }
        Subscriber subscriber = new PosetSubscriber<BulletinResponse>().getSubscriber(callback_bullet);
        UserManager.getBulletins(CURTURNPAGE, subscriber);
    }

    /*设置已读*/
    private void setRead(List<Integer> list_Id){
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_read);
        UserManager.setread(list_Id, subscriber);
    }

    ResponseResultListener callback_bullet = new ResponseResultListener<BulletinDataResponst>() {
        @Override
        public void success(BulletinDataResponst returnMsg) {
            closeProgress();
            mList.addAll(returnMsg.getRows());
            adapter_bullet.clear();
            adapter_bullet.addAll(mList);
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

    ResponseResultListener callback_read = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
        }

        @Override
        public void fialed(String resCode, String message) {
        }
    };
}
