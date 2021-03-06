package com.shidai.yunshang.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.activities.WebActivity;
import com.shidai.yunshang.activities.WebActivity_;
import com.shidai.yunshang.activities.base.BaseActivity;
import com.shidai.yunshang.adapters.UpgradeAdapter;
import com.shidai.yunshang.adapters.WalletAdapter;
import com.shidai.yunshang.adapters.WalletTitleAdapter;
import com.shidai.yunshang.fragments.SJSelectBankcardFragment;
import com.shidai.yunshang.fragments.SJSelectBankcardFragment_;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.AdapterListener;
import com.shidai.yunshang.intefaces.RefreshListener;
import com.shidai.yunshang.managers.UrlAddressManger;
import com.shidai.yunshang.models.BillbagModel;
import com.shidai.yunshang.models.ChannelModel;
import com.shidai.yunshang.networks.ApiClient;
import com.shidai.yunshang.networks.responses.ShowupResponse;
import com.shidai.yunshang.utils.ImageLoader;
import com.shidai.yunshang.utils.SecurePreferences;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.NavBarBack;
import com.shidai.yunshang.view.widget.SwitchPayTypeView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * 创建时间： 2017/7/29.
 * 作者：黄如辉
 * 功能描述：升级授权
 */
@EActivity(R.layout.fragment_upgrade)
public class UpgradeActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;
    @ViewById(R.id.imageView14)
    ImageView imgIcon;
    @ViewById(R.id.txtName)
    TextView txtName;
    @ViewById(R.id.textView34)
    TextView txtFeilv;
    @ViewById(R.id.txtDes)
    TextView txtDes;
    /*选择支付方式*/
//    @ViewById(R.id.mSwitchPayTypeView)
//    SwitchPayTypeView mSwitchPayTypeView;
    @ViewById(R.id.mRecycleView)
    RecyclerView mRecycleView;
    @ViewById(R.id.imageView8)
    ImageView imgHeadView;
    @ViewById(R.id.txtLevel)
    TextView txtLevel;
    @ViewById(R.id.progressBar)
    ProgressBar progressBar;
    @ViewById(R.id.button2)
    Button payGrade;
    @ViewById(R.id.titleRecycle)
    RecyclerView titleRecycle;

    private ShowupResponse showupResponse;
    private UpgradeAdapter adapter_upgrade;
    private WalletTitleAdapter walletTitleAdapter;

    @AfterViews
    void initView(){
        showupResponse = (ShowupResponse) getIntent().getSerializableExtra("showupResponse");
        if (showupResponse.getPayments().size() > 0){
            BillbagModel billbagModel = showupResponse.getPayments().get(0);
            billbagModel.setClick(true);
        }

        txtFeilv.setText("");
        mNavbar.setMiddleTitle(showupResponse.getName());
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });

//        mSwitchPayTypeView.showYLline();
        String userPhoto = SecurePreferences.getInstance().getString("USERPHOTO", "");
        String gradeName = SecurePreferences.getInstance().getString("USERGRADENAME", "");
        int gradeCount = SecurePreferences.getInstance().getInt("USERGRADECOUNT", 0);
        int curturnGrade = SecurePreferences.getInstance().getInt("USERGRADEID", 0);

        ImageLoader.loadCircleImage(Tool.getPicUrl(getActivity(), userPhoto, 48, 48), imgHeadView, R.drawable.dl_tx);
        txtLevel.setText(getResources().getString(R.string.shouquanzizhi) +"  "+ gradeName);
        txtName.setText(showupResponse.getName());
        progressBar.setMax(gradeCount);
        progressBar.setProgress(curturnGrade);

        String pictureUrl = ApiClient.BASE_URL_TEST + "content/images/grade/"+showupResponse.getId()+".png";
        ImageLoader.loadImage(pictureUrl, imgIcon);

        //默认显示银联支付
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setAdapter(adapter_upgrade = new UpgradeAdapter());
        adapter_upgrade.setCode("UNIONPAY");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        titleRecycle.setLayoutManager(linearLayoutManager);
        titleRecycle.setAdapter(walletTitleAdapter = new WalletTitleAdapter(adapterListener));
        walletTitleAdapter.addAll(showupResponse.getPayments());

        txtDes.setText(showupResponse.getRemark());

        if (showupResponse != null && showupResponse.getPayments().size() > 0){
            List<ChannelModel> listBill = showupResponse.getPayments().get(0).getChannel();
            adapter_upgrade.addAll(listBill);
        }

        if (showupResponse.is_show()){
            payGrade.setVisibility(View.VISIBLE);
        }else{
            payGrade.setVisibility(View.INVISIBLE);
        }

        if (showupResponse.is_online()){
            //不可升级，联系客服
            //显示升级按钮
            payGrade.setText("支付¥" +showupResponse.getUp_fee());
        }else{
            payGrade.setText("联系我们");
        }

    }

    AdapterListener adapterListener = new AdapterListener<BillbagModel>() {
        @Override
        public void setItemClickListener(BillbagModel o, int position) {
            String code = showupResponse.getPayments().get(position).getCode();
            adapter_upgrade.clear();
            if (showupResponse != null){
                for (BillbagModel billbagModel : showupResponse.getPayments()){
                    billbagModel.setClick(false);
                }
                for (int i=0; i<showupResponse.getPayments().size(); i++){
                    BillbagModel billbagModel = showupResponse.getPayments().get(i);
                    if (position == i){
                        billbagModel.setClick(true);
                        break;
                    }
                }
                walletTitleAdapter.replaceWith(showupResponse.getPayments());
                BillbagModel billbagModel = showupResponse.getPayments().get(position);
                List<ChannelModel> listChannel = billbagModel.getChannel();
                adapter_upgrade.setCode(code);
                adapter_upgrade.clear();
                adapter_upgrade.addAll(listChannel);
            }
        }
    };

    @Click(R.id.button2)
    void payGrade(){
        if (showupResponse.is_online()){
            //显示支付
            SJSelectBankcardFragment fragment = SJSelectBankcardFragment_.builder().build();
            Bundle bundle = new Bundle();
            bundle.putDouble("upgradeMoney", showupResponse.getUp_fee());
            bundle.putInt("gradeid", showupResponse.getId());
            fragment.setArguments(bundle);
            showFragment(fragment);
        }else{
            //不可升级，联系客服
            Intent intent = new Intent(getActivity(), WebActivity_.class);
            intent.putExtra("titleBar", "我要代理");
            intent.putExtra("webUrl", UrlAddressManger.GOPROXY);
            startActivity(intent);
        }
    }

    @Subscribe
    public void refreshData(RefreshListener refreshListener){
        if (refreshListener.tag.equals("finishFragment") && refreshListener.refresh){
            finish();
        }
    }

}
