package com.shidai.yunshang.fragments;

import android.app.Fragment;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.TransferResponse;
import com.shidai.yunshang.utils.SecurePreferences;
import com.shidai.yunshang.utils.ToastUtil;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;

import rx.Subscriber;

/**
 * 创建时间： 2017/7/29.
 * 作者：黄如辉
 * 功能描述：分润结算
 */
@EFragment(R.layout.fragment_jiesuan)
public class JiesuanFragment extends BaseFragment{
    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;

    @ViewById(R.id.editText2)
    EditText editText;
    @ViewById(R.id.textView44)
    TextView txtAll;
    @ViewById(R.id.txtRemark)
    TextView txtRemark;

    private double totalMoney;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        totalMoney = getArguments().getDouble("totalMoney");
    }

    @AfterViews
    void initView(){
        mNavbar.setMiddleTitle("结算");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });

        String tranfermin = SecurePreferences.getInstance().getString("MINTRANSFER", "");

        /*全部*/
        txtAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(totalMoney+"");
            }
        });
//        txtRemark.setText("备注: 单次提现不少于¥"+tranfermin +"    可提现金额: ¥" + totalMoney);
    }

    /*提现*/
    @Click(R.id.button2)
    void tixian(){

    }



}
