package com.shidai.yunshang.fragments;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.adapters.TextAdapter;
import com.shidai.yunshang.constants.Constant;
import com.shidai.yunshang.fragments.base.BasePullRecyclerFragment;
import com.shidai.yunshang.intefaces.AdapterListener;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.models.BranchbrankModel;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.BranchBankResponse;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.NavBarBack;
import com.shidai.yunshang.view.widget.NavBarSearch;
import com.shidai.yunshang.view.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 创建时间： 2017/8/2.
 * 作者：黄如辉
 * 功能描述：开户支行
 */
@EFragment(R.layout.fragment_khzh)
public class KHZHFragment extends BasePullRecyclerFragment{

    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;
    @ViewById(R.id.mToolbar)
    NavBarSearch mToolbar;

    private String bank_code, province_id, city_id;
    private int CURTURNPAGE = Constant.DEFAULTPAGE;
    private TextAdapter adapter_txt;
    private List<BranchbrankModel> list_brank = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bank_code = getArguments().getString("bankcode");
        province_id = getArguments().getString("provinceid");
        city_id = getArguments().getString("cityid");
    }

    @Override
    protected void initView(PullRecyclerView recyclerView) {
        //获取银行支行
        getBranchbank();

        mNavbar.setMiddleTitle("选择支行");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter( adapter_txt = new TextAdapter(adapterListener));

        mToolbar.onSeacherListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    CURTURNPAGE = Constant.DEFAULTPAGE;
                    list_brank.clear();
                    getBranchbank();
                    Tool.hideInputMethod(getActivity(), mNavbar);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onRefresh(RecyclerView recyclerView) {
        CURTURNPAGE = Constant.DEFAULTPAGE;
        list_brank.clear();
        getBranchbank();
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        CURTURNPAGE++;
        getBranchbank();
    }

    AdapterListener adapterListener = new AdapterListener<BranchbrankModel>() {
        @Override
        public void setItemClickListener(BranchbrankModel o, int position) {
            EventBus.getDefault().post(o);
            finishFragment();
        }
    };


    private void getBranchbank() {
        String searchTxt = mToolbar.getSearchText().toString();
        if (CURTURNPAGE == Constant.DEFAULTPAGE){
            showProgress();
        }
        Subscriber subscriber = new PosetSubscriber<BranchBankResponse>().getSubscriber(callback_getbranch);
        UserManager.getBranchbank(bank_code, searchTxt, province_id, city_id, CURTURNPAGE, subscriber);
    }

    ResponseResultListener callback_getbranch = new ResponseResultListener<BranchBankResponse>() {
        @Override
        public void success(BranchBankResponse returnMsg) {
            closeProgress();
            if (returnMsg.getTotal_pages() <= CURTURNPAGE){
                finishLoad(false);
            }else{
                finishLoad(true);
            }

            list_brank.addAll(returnMsg.getRows());
            adapter_txt.addAll(list_brank);
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

}
