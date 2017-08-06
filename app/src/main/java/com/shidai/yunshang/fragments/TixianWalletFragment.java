package com.shidai.yunshang.fragments;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.adapters.WalletTixianAdapter;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.AdapterListener;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.SettletypeResponse;
import com.shidai.yunshang.utils.SecurePreferences;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 创建时间： 2017/8/7.
 * 作者：黄如辉
 * 功能描述：钱包结算
 */
@EFragment(R.layout.fragment_tixianwallet)
public class TixianWalletFragment extends BaseFragment {

    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;
    @ViewById(R.id.editText2)
    EditText edtMoney;
    @ViewById(R.id.textView44)
    TextView txtAll;
    @ViewById(R.id.textView48)
    TextView txtRemak;
    @ViewById(R.id.mRecycleView)
    RecyclerView mRecycleView;

    private ArrayList<SettletypeResponse> listResponse = new ArrayList<>();
    private WalletTixianAdapter adapter_wallettixian;
    private String tixianMoney="";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        tixianMoney = getArguments().getString("tixianMoney");
    }

    @AfterViews
    void initView(){
        mNavbar.setMiddleTitle("提现");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });

        /*获取提现结算方式*/
        getSettletype();

        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setAdapter(adapter_wallettixian = new WalletTixianAdapter(adapterListener));

        String mintransfer = SecurePreferences.getInstance().getString("MINTRANSFER", "");
        txtRemak.setText("备注: 单次提现不少于¥" + tixianMoney + "   可提现金额: ¥"+ Tool.formatPrice(mintransfer));

        txtAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtMoney.setText(tixianMoney);
            }
        });
    }

    /*选择费率*/
    AdapterListener adapterListener = new AdapterListener<SettletypeResponse>() {
        @Override
        public void setItemClickListener(SettletypeResponse o, int position) {
            for (SettletypeResponse response : listResponse){
                response.setClick(false);
            }
            for (int i =0;i<listResponse.size();i++){
                if (position == i){
                    SettletypeResponse settletypeResponse = listResponse.get(i);
                    settletypeResponse.setClick(true);
                    break;
                }
            }
            adapter_wallettixian.replaceWith(listResponse);
        }
    };

    private void getSettletype() {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<List<SettletypeResponse>>().getSubscriber(callback_settletype);
        UserManager.getSettletype(subscriber);
    }

    /*提现*/
    @Click(R.id.button2)
    void commitTixian(){

    }


    ResponseResultListener callback_settletype = new ResponseResultListener<List<SettletypeResponse>>() {
        @Override
        public void success(List<SettletypeResponse> returnMsg) {
            closeProgress();
            listResponse.clear();
            listResponse.addAll(returnMsg);
            adapter_wallettixian.clear();
            adapter_wallettixian.addAll(listResponse);
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };
}
