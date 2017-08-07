package com.shidai.yunshang.fragments;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.MerchantDetailResponse;
import com.shidai.yunshang.utils.ImageLoader;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import rx.Subscriber;

/**
 * 创建时间： 2017/8/7.
 * 作者：黄如辉
 * 功能描述：商户详情
 */
@EFragment(R.layout.fragment_mechantdetail)
public class MechantDetailFragment extends BaseFragment{

    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;
    @ViewById(R.id.txtBanben)
    TextView txtBanben;
    @ViewById(R.id.txtStatus)
    TextView txtStatus;
    @ViewById(R.id.textView66)
    TextView txtPhone;
    @ViewById(R.id.textView65)
    TextView txtName;
    @ViewById(R.id.imageView24)
    ImageView imgHeadView;
    /*注册时间*/
    @ViewById(R.id.textView69)
    TextView txtCreatTime;
    /*实名认证*/
    @ViewById(R.id.txtShimingStatus)
    TextView txtShimingStatu;
    /*推荐人*/
    @ViewById(R.id.txtTuijianren)
    TextView txtTuijianren;
    /*本月流水贡献*/
    @ViewById(R.id.textView74)
    TextView txLliushuiGongxian;
    /*本月分润贡献*/
    @ViewById(R.id.textView75)
    TextView txtFenrunGongxian;
    /*下级分销商数*/
    @ViewById(R.id.txtMechantCount)
    TextView txtFenxiaoCount;

    private int mechantId;
    private String cardType;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mechantId = getArguments().getInt("mechantId", 0);
        cardType = getArguments().getString("cardType");
    }

    @AfterViews
    void initView(){
        mNavbar.setMiddleTitle("商户资料");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });
        getMechantDetail();
        if (cardType.equals("2")){
            //直接分销
            txtPhone.setVisibility(View.GONE);
            txtBanben.setVisibility(View.GONE);
        }else{
            txtBanben.setVisibility(View.VISIBLE);
            txtPhone.setVisibility(View.VISIBLE);
        }
    }

    private void getMechantDetail() {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<MerchantDetailResponse>().getSubscriber(callback_mechantdetail);
        UserManager.getMerchantDetail(mechantId, subscriber);
    }

    ResponseResultListener callback_mechantdetail = new ResponseResultListener<MerchantDetailResponse>() {
        @Override
        public void success(MerchantDetailResponse returnMsg) {
            closeProgress();
            txtName.setText(returnMsg.getName());
            txtStatus.setText(returnMsg.getAuth_status_name());
            txtBanben.setText(returnMsg.getGrade_name());
            txtPhone.setText("手机号: "+returnMsg.getMobile());
            if (returnMsg.is_receipt()){
                //已收款
                txtStatus.setText("已收款");
            }else{
                txtStatus.setText("未收款");
            }
            txtCreatTime.setText(returnMsg.getCreate_time());
            txtShimingStatu.setText(returnMsg.getAuth_status_name());
            txtTuijianren.setText(returnMsg.getRecommender());
            txLliushuiGongxian.setText("¥"+returnMsg.getMouth_proxy());
            txtFenrunGongxian.setText("¥"+returnMsg.getMouth_profit());
            txtFenxiaoCount.setText(returnMsg.getChilds()+"名");
            ImageLoader.loadCircleImage(Tool.getPicUrl(getActivity(), returnMsg.getPhoto(), 44, 44), imgHeadView, R.drawable.dj_yh);
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

}
