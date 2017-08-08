package com.shidai.yunshang.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.activities.base.BaseActivity;
import com.shidai.yunshang.constants.Constant;
import com.shidai.yunshang.constants.Permission;
import com.shidai.yunshang.fragments.ErweimaFragment;
import com.shidai.yunshang.fragments.ErweimaFragment_;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.ImageUploadListener;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.PermissonManager;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.UploadFileWithoutLoding;
import com.shidai.yunshang.utils.FileUtil;
import com.shidai.yunshang.utils.ImageLoader;
import com.shidai.yunshang.utils.PermissionUtili;
import com.shidai.yunshang.utils.SecurePreferences;
import com.shidai.yunshang.utils.ToastUtil;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;
import rx.Subscriber;

import static android.app.Activity.RESULT_OK;

/**
 * 创建时间： 2017/8/3.
 * 作者：黄如辉
 * 功能描述：个人资料
 */
@EActivity(R.layout.fragment_person)
public class PersonMessageActivity extends BaseActivity{

    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;
    /*更换头像*/
    @ViewById(R.id.relativeLayout10)
    RelativeLayout relativeLayout10;
    /*二维码*/
    @ViewById(R.id.relaErweima)
    RelativeLayout relaErweima;

    @ViewById(R.id.imgHead)
    ImageView imgHead;
    @ViewById(R.id.txtNameRight)
    TextView txtNameRight;

    /*获取图片列表*/
    private ArrayList<String> photos = new ArrayList<>();
    /*缓存的头像*/
    private String imgpathMemery;

    @AfterViews
    void initVeiw(){

        mNavbar.setMiddleTitle("个人资料");
        mNavbar.setOnMenuClickListener(new NavBarBack.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });


        String photo = SecurePreferences.getInstance().getString("USERPHOTO", "");
        String username = SecurePreferences.getInstance().getString("USERNAME", "");
        ImageLoader.loadCircleImage(Tool.getPicUrl(getActivity(), photo, 68, 68), imgHead, R.drawable.dl_tx);
        txtNameRight.setText(username);


        /*更换头像*/
        relativeLayout10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permission = new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE};
                boolean checked =  PermissionUtili.checkPermission(getActivity(), permission, "需要设置手机权限", "需要使用相机和读取相册权限，请到设置中设置应用权限。");
                if (checked ) {
                    PermissonManager permissonManager = new PermissonManager(getActivity());
                    permissonManager.lacksPermissions();
                    PhotoPicker.builder()
                            .setPhotoCount(1)
                            .setShowCamera(true)
                            .setShowGif(false)
                            .setPreviewEnabled(false)
//                .setSelected(photos)
                            .start(getActivity(), PhotoPicker.REQUEST_CODE);
                }
            }
        });

        /*二维码*/
        relaErweima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErweimaFragment fragment = ErweimaFragment_.builder().build();
                Bundle bundle = new Bundle();
                bundle.putString("type", "1");
                fragment.setArguments(bundle);
                showFragment(fragment);
            }
        });
    }


    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                ImageLoader.loadCircleImage(photos.get(0), imgHead, R.drawable.dl_tx);
            }
            showProgress();
            if (photos.size() > 0){
                new UploadFileWithoutLoding(uploadListener).execute(photos.get(0), Constant.UPLOADHEADURL);
            }else{
                closeProgress();
            }
        }
    }


    ImageUploadListener uploadListener = new ImageUploadListener(){

        @Override
        public void finishLoading(String imgPath, int position) {
            //去上传头像
            imgpathMemery = imgPath;
            Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_headphoto);
            UserManager.saveHeadPhoto(imgPath, subscriber);
        }
    };

    /*头像保存回调*/
    ResponseResultListener callback_headphoto = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            closeProgress();
            if (returnMsg){
                ToastUtil.showToast("保存成功");
            }else{
                ToastUtil.showToast("保存失败");
            }

            ImageLoader.loadCircleImage(Tool.getPicUrl(getActivity(), imgpathMemery, 68, 68), imgHead, R.drawable.dl_tx);
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
            ToastUtil.showToast("保存失败");
        }
    };

}
