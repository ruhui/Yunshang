package com.shidai.yunshang.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.adapters.MechantListAdapter;
import com.shidai.yunshang.constants.Constant;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.fragments.base.BasePullRecyclerFragment;
import com.shidai.yunshang.intefaces.AdapterListener;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.models.MechantModel;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.MechantListResponse;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.NavBarSearch;
import com.shidai.yunshang.view.widget.NavBarSwitch;
import com.shidai.yunshang.view.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 创建时间： 2017/8/7.
 * 作者：黄如辉
 * 功能描述：直接分销、间接分销
 */
@EFragment(R.layout.fragment_mechatlist)
public class MechatListFragment extends BasePullRecyclerFragment {

    @ViewById(R.id.mNavbar)
    NavBarSwitch mNavbar;
    @ViewById(R.id.mToolbar)
    NavBarSearch mToolbar;

    private String cardType = "1";//1 直接分销  2间接分销
    private int CURTURNPAGE = Constant.DEFAULTPAGE;
    private int gradeid = 0;
    private MechantListAdapter adapter_mechant;
    private List<MechantModel> list_mechant = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        gradeid = getArguments().getInt("gradeid");
    }

    @Override
    protected void initView(PullRecyclerView recyclerView) {
        getMechantList();
        mNavbar.setLeftTitle("直接分销");mNavbar.setTxtRightView("间接分销");
        mNavbar.setOnMenuClickListener(new NavBarSwitch.OnMenuClickListener() {
            @Override
            public void onBackClick(View view) {
                super.onBackClick(view);
                finishFragment();
            }

            @Override
            public void onXyMenuClick(View view) {
                super.onXyMenuClick(view);
                //直接分销
                list_mechant.clear();
                cardType = "1";
                CURTURNPAGE = Constant.DEFAULTPAGE;
                getMechantList();
            }

            @Override
            public void onBankMenuClick(View view) {
                super.onBankMenuClick(view);
                //间接分销
                list_mechant.clear();
                cardType = "2";
                CURTURNPAGE = Constant.DEFAULTPAGE;
                getMechantList();
            }
        });


        mToolbar.onSeacherListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    CURTURNPAGE = Constant.DEFAULTPAGE;
                    list_mechant.clear();
                    getMechantList();
                    Tool.hideInputMethod(getActivity(), mNavbar);
                    return true;
                }
                return false;
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter_mechant = new MechantListAdapter(adapterListener));
    }


    AdapterListener adapterListener = new AdapterListener<MechantModel>() {
        @Override
        public void setItemClickListener(MechantModel o, int position) {
            //详情
            MechantDetailFragment fragment = MechantDetailFragment_.builder().build();
            Bundle bundle = new Bundle();
            bundle.putInt("mechantId", o.getId());
            bundle.putString("cardType", cardType);
            fragment.setArguments(bundle);
            showFragment(getActivity(), fragment);
        }
    };

    @Override
    protected void onRefresh(RecyclerView recyclerView) {
        CURTURNPAGE = Constant.DEFAULTPAGE;
        list_mechant.clear();
        getMechantList();
        Tool.hideInputMethod(getActivity(), mNavbar);
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        CURTURNPAGE++;
        getMechantList();
    }

    /*获取商户*/
    private void getMechantList(){
        String searchTxt = mToolbar.getSearchText().toString();
        Subscriber subscriber = new PosetSubscriber<MechantListResponse>().getSubscriber(callback_mechant);
        UserManager.getMechantList(String.valueOf(gradeid), searchTxt, cardType, CURTURNPAGE, subscriber);
    }

    ResponseResultListener callback_mechant = new ResponseResultListener<MechantListResponse>() {
        @Override
        public void success(MechantListResponse returnMsg) {
            if (returnMsg.getTotal_pages() < CURTURNPAGE){
                finishLoad(false);
            }else{
                finishLoad(true);
            }
            list_mechant.addAll(returnMsg.getRows());
            adapter_mechant.clear();
            adapter_mechant.addAll(list_mechant);
        }

        @Override
        public void fialed(String resCode, String message) {
        }
    };
}
