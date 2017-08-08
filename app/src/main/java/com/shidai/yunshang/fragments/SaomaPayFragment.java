package com.shidai.yunshang.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/8/8.
 * 作者：黄如辉
 * 功能描述：
 */
@EFragment(R.layout.fragment_sanmapay)
public class SaomaPayFragment extends BaseFragment {
    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;
    @ViewById(R.id.textView79)
    TextView txttOP;
    @ViewById(R.id.imageView26)
    ImageView imgErweima;
    @ViewById(R.id.imgWeixin)
    ImageView imgWeixin;
    @ViewById(R.id.imgZhifubao)
    ImageView imgZhifubao;
    @ViewById(R.id.txtDes)
    TextView txtDes;

    private String topString1 = "商家";
    private String topString2 = "正在向您发起一笔金额为¥";
    private String topString3 = "的微信收款，请用微信扫描以下二维码收款";

    private String bottomStr1 = "温馨提示：如您不认识";
    private String bottomStr2 = "或者不是您的好友，请谨慎操作。凡任何以兼职、信用卡套现、养卡、提额、淘宝刷单、系统延迟为由的均为诈骗！如有疑问，请拨打客服热线：400-888-9876";

    @AfterViews
    void initView(){
        mNavbar.setMiddleTitle("扫码支付");mNavbar.setRightTxt("分享");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });


    }

    /*确认*/
    @Click(R.id.button2)
    void sureSubmit(){

    }

}
