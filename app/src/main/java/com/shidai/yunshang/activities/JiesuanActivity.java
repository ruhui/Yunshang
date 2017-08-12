package com.shidai.yunshang.activities;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.activities.base.BaseActivity;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.RefreshListener;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.TransferResponse;
import com.shidai.yunshang.utils.SecurePreferences;
import com.shidai.yunshang.utils.ToastUtil;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;

/**
 * 创建时间： 2017/7/29.
 * 作者：黄如辉
 * 功能描述：分润结算
 */
@EActivity(R.layout.fragment_jiesuan)
public class JiesuanActivity extends BaseActivity{
    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;

    @ViewById(R.id.editText2)
    EditText editText;
    @ViewById(R.id.textView44)
    TextView txtAll;
    @ViewById(R.id.txtRemark)
    TextView txtRemark;

    private double totalMoney;

    @AfterViews
    void initView(){
        totalMoney = getIntent().getDoubleExtra("totalMoney", 0);
        mNavbar.setMiddleTitle("结算");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });

        /*全部*/
        txtAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(totalMoney+"");
            }
        });
    }

    /*提现*/
    @Click(R.id.button2)
    void tixian(){
        String inputMoney = editText.getText().toString();
        if (TextUtils.isEmpty(inputMoney)){
            ToastUtil.showToast("请输入金额");
            return;
        }
//        if (Double.valueOf(inputMoney) == 0 || Double.valueOf(inputMoney) < Double.valueOf(tranfermin)){
//            ToastUtil.showToast("单次提现不少于¥"+tranfermin );
//            return;
//        }
        showProgress();
        Subscriber subscribe = new PosetSubscriber<TransferResponse>().getSubscriber(callback_transfer);
        UserManager.setTransfer(Double.valueOf(inputMoney), "", 2, subscribe);
    }


    ResponseResultListener callback_transfer = new ResponseResultListener<TransferResponse>() {
        @Override
        public void success(TransferResponse returnMsg) {
            closeProgress();
            EventBus.getDefault().post(new RefreshListener(true));
            Intent intent = new Intent(getActivity(), SuccessTixianActivity_.class);
            intent.putExtra("tranferRes", returnMsg);
            startActivity(intent);
            finish();
//            SuccessTixianActivity fragment_tranfer = SuccessTixianFragment_.builder().build();
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("tranferRes", returnMsg);
//            fragment_tranfer.setArguments(bundle);
//            showFragment(getActivity(), fragment_tranfer);

        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

}
