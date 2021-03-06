package com.shidai.yunshang.fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.constants.Constant;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.RecommenderMsgResponse;
import com.shidai.yunshang.utils.ImageLoader;
import com.shidai.yunshang.utils.QRCodeUtil;
import com.shidai.yunshang.utils.SecurePreferences;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.lang.reflect.Type;

import rx.Subscriber;

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

    private String type = "1";//1是自己，2是推荐人

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        type = getArguments().getString("type");
        if (TextUtils.isEmpty(type)) {
            type = "1";
        }
    }

    @AfterViews
    void initView(){

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


        if (type.equals("1")){
            mNavbar.setMiddleTitle("我的二维码");
            String qRcode = SecurePreferences.getInstance().getString("USERQRCODE", "");
            String telePhone = SecurePreferences.getInstance().getString("USERMOBILE", "");
            String photo = SecurePreferences.getInstance().getString("USERPHOTO", "");
            String username = SecurePreferences.getInstance().getString("USERNAME", "");
            ImageLoader.loadCircleImage(Tool.getPicUrl(getActivity(), photo, 68, 68), imgHead, R.drawable.dl_tx);
            txtName.setText(username);
            txtZhanghao.setText("账号："+telePhone);
            setErweima(qRcode);
            txtDes.setText(bottomStrbrf + username + bottomStraft);
        }else{
            mNavbar.setMiddleTitle("推荐人二维码");
            getRemendMesg();
        }
    }

    /*生成二维码*/
    private void setErweima(final String qcodeUil) {
        final String filePath = Constant.ERWEIMA + File.separator
                + "qr_" + System.currentTimeMillis() + ".jpg";

        //二维码图片较大时，生成图片、保存文件的时间可能较长，因此放在新线程中
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean success = QRCodeUtil.createQRImage(qcodeUil.trim(), 253, 247,
                        true ? BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher) : null,
                        filePath);

                if (success) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imgErweima.setImageBitmap(BitmapFactory.decodeFile(filePath));
                        }
                    });
                }
            }
        }).start();
    }

    /*获取推荐人二维码*/
    private void getRemendMesg() {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<RecommenderMsgResponse>().getSubscriber(callback_recommend);
        UserManager.getRecommender(subscriber);
    }

    ResponseResultListener callback_recommend = new ResponseResultListener<RecommenderMsgResponse>() {
        @Override
        public void success(RecommenderMsgResponse returnMsg) {
            closeProgress();
            txtName.setText(returnMsg.getName());
            txtZhanghao.setText("账号："+ returnMsg.getMobile());
            txtDes.setText(bottomStrbrf + returnMsg.getName() + bottomStraft);
            setErweima(returnMsg.getQrcode());
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

}
