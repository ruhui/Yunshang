package com.shidai.yunshang.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.utils.ImageLoader;
import com.shidai.yunshang.utils.SecurePreferences;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/8/3.
 * 作者：黄如辉
 * 功能描述：我的二维码
 */
@EFragment(R.layout.frament_erweima)
public class ErweimaFragment extends BaseFragment {

    private String bottomStrbrf = "温馨提示：如您不认识";
    private String bottomStraft = "或者不是您的好友，请谨慎操作。凡任何以兼职、信用卡套现、养卡、提额、淘宝刷单、系统延迟为由的均为诈骗！如有疑问，请拨打客服热线：400-888-9876";

    @ViewById(R.id.txtName)
    TextView txtName;
    @ViewById(R.id.txtZhanghao)
    TextView txtZhanghao;
    @ViewById(R.id.imgHead)
    ImageView imgHead;
    @ViewById(R.id.imgErweima)
    ImageView imgErweima;
    @ViewById(R.id.txtDes)
    TextView txtDes;
    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;

    @AfterViews
    void initView(){
        mNavbar.setMiddleTitle("我的二维码");
        mNavbar.setRightTxt("分享");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }

            @Override
            public void onRightMenuClick(View view) {
                super.onRightMenuClick(view);
                /*分享*/
            }
        });
        String qRcode = SecurePreferences.getInstance().getString("USERQRCODE", "");
        String telePhone = SecurePreferences.getInstance().getString("USERMOBILE", "");
        String photo = SecurePreferences.getInstance().getString("USERPHOTO", "");
        String username = SecurePreferences.getInstance().getString("USERNAME", "");
        ImageLoader.loadCircleImage(Tool.getPicUrl(getActivity(), photo, 68, 68), imgHead, R.drawable.dl_tx);
        txtName.setText(username);
        txtZhanghao.setText("账号："+telePhone);
        ImageLoader.loadImage(Tool.getPicUrl(getActivity(), qRcode, 253, 247), imgErweima);
        txtDes.setText(bottomStrbrf + username + bottomStraft);
    }

}
