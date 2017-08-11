package com.shidai.yunshang.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.activities.InputMoneyActivity;
import com.shidai.yunshang.constants.Constant;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.CreatQcodeResponse;
import com.shidai.yunshang.utils.LogUtil;
import com.shidai.yunshang.utils.QRCodeUtil;
import com.shidai.yunshang.utils.ScreenShot;
import com.shidai.yunshang.utils.SecurePreferences;
import com.shidai.yunshang.utils.ToastUtil;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.NavBarBack;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.File;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * 创建时间： 2017/8/8.
 * 作者：黄如辉
 * 功能描述：扫码支付
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
    @ViewById(R.id.mScroller)
    ScrollView mScroller;

    private String payTypeDes = "微信";

    private String topString1 = "商家";
    private String topString2 = "正在向您发起一笔金额为¥";
    private String topString3 = "的";
    private String topString4 = "收款，请用";
    private String topString5 = "扫描以下二维码收款";

    private String bottomStr1 = "温馨提示：如您不认识";
    private String bottomStr2 = "或者不是您的好友，请谨慎操作。凡任何以兼职、信用卡套现、养卡、提额、淘宝刷单、系统延迟为由的均为诈骗！如有疑问，请拨打客服热线：";
    private String payType;
    private CreatQcodeResponse qcodeData;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        payType = getArguments().getString("payType");
        qcodeData = (CreatQcodeResponse) getArguments().getSerializable("qcodeData");
    }

    @AfterViews
    void initView(){

        String teltPhone = SecurePreferences.getInstance().getString("SERVERPHONE", "");

        ((InputMoneyActivity)getActivity()).setErweimaShow();

        setPayTypeDes();

        txtDes.setText(bottomStr1 + qcodeData.getUsername() + bottomStr2 + teltPhone);

        if (payType.equals("ALIPAY_JS")){
            mNavbar.setMiddleTitle("支付宝支付码");
        }else{
            mNavbar.setMiddleTitle("微信支付码");
        }
        mNavbar.setRightTxt("保存");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }

            @Override
            public void onRightMenuClick(View view) {
                super.onRightMenuClick(view);
                //下载需要写SD卡权限, targetSdkVersion>=23 需要动态申请权限
                RxPermissions.getInstance(getActivity())
                        // 申请权限
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean granted) {
                                if(granted){
                                    //请求成功
                                    String fname = ScreenShot.savePic(ScreenShot.compressImage(ScreenShot
                                            .getBitmapByView( mScroller)));
                                    //这个广播的目的就是更新图库，发了这个广播进入相册就可以找到你保存的图片了！
                                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                    Uri uri = Uri.fromFile(new File(fname));
                                    intent.setData(uri);
                                    getActivity().sendBroadcast(intent);
                                    ToastUtil.showToast("保存成功，可在相册中查看.");
                                }
                            }
                        });
            }
        });


        setErweima(qcodeData.getNative_url());
    }

    private void setErweima(final String qcodeUil) {
        final String filePath = Constant.ERWEIMA + File.separator
                + "qr_" + System.currentTimeMillis() + ".jpg";

        //二维码图片较大时，生成图片、保存文件的时间可能较长，因此放在新线程中
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean success = QRCodeUtil.createQRImage(qcodeUil.trim(), 500, 500,
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


    private void setPayTypeDes(){
        if (payType.equals("ALIPAY_JS")){
            payTypeDes = "支付宝";
        }else{
            payTypeDes = "微信";
        }

        txttOP.setText(topString1 + qcodeData.getUsername() + topString2 +
                Tool.formatPrice(qcodeData.getAmount())+topString3 + payTypeDes + topString4 + payTypeDes + topString5);
    }

    /*微信二维码*/
    @Click(R.id.imgWeixin)
    void weixinErweima(){
        RxPermissions.getInstance(getActivity())
                // 申请权限
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if(granted){
                            //请求成功
                            payType = "WXPAY_JS";
                            selectPay();
                        }
                    }
                });

    }


    /*支付宝二维码*/
    @Click(R.id.imgZhifubao)
    void zfbErweima(){
        RxPermissions.getInstance(getActivity())
                // 申请权限
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if(granted){
                            //请求成功
                            payType = "ALIPAY_JS";
                            selectPay();
                        }
                    }
                });
    }

    private void selectPay() {
        Subscriber subscriber = new PosetSubscriber<String>().getSubscriber(callback_erweima);
        UserManager.selectPay(payType, subscriber);
    }

    ResponseResultListener callback_erweima = new ResponseResultListener<String>() {
        @Override
        public void success(String returnMsg) {
            setErweima(returnMsg);
            setPayTypeDes();
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };

}
