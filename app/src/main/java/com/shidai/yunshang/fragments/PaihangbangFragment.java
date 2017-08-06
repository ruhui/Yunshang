package com.shidai.yunshang.fragments;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.adapters.SortAdapter;
import com.shidai.yunshang.constants.Constant;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.SortResponse;
import com.shidai.yunshang.utils.ImageLoader;
import com.shidai.yunshang.utils.LogUtil;
import com.shidai.yunshang.utils.SecurePreferences;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.NavBarSwitch;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import rx.Subscriber;

/**
 * 创建时间： 2017/7/29.
 * 作者：黄如辉
 * 功能描述：排行榜
 */
@EFragment(R.layout.fragment_paihangbang)
public class PaihangbangFragment extends BaseFragment {
    @ViewById(R.id.mNavbar)
    NavBarSwitch mNavbar;

    @ViewById(R.id.textView45)
    TextView txtMouthBenefit;
    @ViewById(R.id.relaTop)
    RelativeLayout relaTop;
    @ViewById(R.id.mRecycleView)
    RecyclerView mRecycleView;
    @ViewById(R.id.txtUsername)
    TextView txtUsername;
    @ViewById(R.id.imageView19)
    ImageView imgHead;


    private String cardType = "1";//1 好友榜  2江湖榜
    private double mouthBenefit = 0;
    private SortAdapter adapter_sort;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mouthBenefit = getArguments().getDouble("mouthBenefit");
    }

    @AfterViews
    void initView(){
        getSorter();
        relaTop.setVisibility(View.GONE);
        mNavbar.setLeftTitle("好友榜");mNavbar.setTxtRightView("江湖榜");
        mNavbar.setOnMenuClickListener(new NavBarSwitch.OnMenuClickListener() {
            @Override
            public void onBackClick(View view) {
                super.onBackClick(view);
                finishFragment();
            }

            @Override
            public void onXyMenuClick(View view) {
                super.onXyMenuClick(view);
                cardType = "1";
                relaTop.setVisibility(View.GONE);
                getSorter();
            }

            @Override
            public void onBankMenuClick(View view) {
                super.onBankMenuClick(view);
                cardType = "2";
                relaTop.setVisibility(View.VISIBLE);
                getSorter();
            }
        });

        txtMouthBenefit.setText("¥ " +mouthBenefit);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setAdapter(adapter_sort = new SortAdapter(cardType));

        String photo = SecurePreferences.getInstance().getString("USERPHOTO", "");
        String username = SecurePreferences.getInstance().getString("USERNAME", "");
        ImageLoader.loadCircleImage(Tool.getPicUrl(getActivity(), photo, 44, 44), imgHead, R.drawable.dl_tx);
        txtUsername.setText(username);
    }

    private void getSorter(){
        Subscriber subscriber = new PosetSubscriber<List<SortResponse>>().getSubscriber(callback_sort);
        UserManager.getSorter(cardType, subscriber);
    }

    ResponseResultListener callback_sort = new ResponseResultListener<List<SortResponse>>() {
        @Override
        public void success(List<SortResponse> returnMsg) {
            adapter_sort.clear();
            adapter_sort.setType(cardType);
            adapter_sort.addAll(returnMsg);
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed","fialed");
        }
    };

}
