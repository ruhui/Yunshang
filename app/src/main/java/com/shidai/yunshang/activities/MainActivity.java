package com.shidai.yunshang.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.shidai.yunshang.MyApplication;
import com.shidai.yunshang.R;
import com.shidai.yunshang.activities.base.BaseActivity;
import com.shidai.yunshang.constants.Constant;
import com.shidai.yunshang.fragments.AuthorizationFragment;
import com.shidai.yunshang.fragments.AuthorizationFragment_;
import com.shidai.yunshang.fragments.HomeFragment_;
import com.shidai.yunshang.fragments.MineFragment_;
import com.shidai.yunshang.fragments.SharebenefitFragment_;
import com.shidai.yunshang.fragments.WalletFragment_;
import com.shidai.yunshang.intefaces.AcitivtyFinishListener;
import com.shidai.yunshang.intefaces.ActivityFinish;
import com.shidai.yunshang.intefaces.DownLoadListener;
import com.shidai.yunshang.intefaces.RefreshListener;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.DownloadTask;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.LoginResponse;
import com.shidai.yunshang.networks.responses.TipsMsgResponse;
import com.shidai.yunshang.networks.responses.UsermsgResponse;
import com.shidai.yunshang.networks.responses.VersionResponst;
import com.shidai.yunshang.utils.ImageLoader;
import com.shidai.yunshang.utils.LogUtil;
import com.shidai.yunshang.utils.SecurePreferences;
import com.shidai.yunshang.utils.ToastUtil;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.utils.Utils;
import com.shidai.yunshang.view.widget.NoScrollViewPager;
import com.shidai.yunshang.view.widget.dialogs.MyAlertDialog;
import com.shidai.yunshang.view.widget.dialogs.UploadAlertDialog;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.viewpagerindicator.IconPagerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.Subscriber;
import rx.functions.Action1;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewById(R.id.tab)
    TabLayout mTab;

    @ViewById(R.id.pager)
    NoScrollViewPager mPager;

    private boolean isdownLoad = false;
    private DownloadTask downloadTask;

    @AfterViews
    void initView(){
        JPushInterface.resumePush(getActivity());
        setup();
        /*开启倒计时*/
        restart();
        /*获取版本号*/
        getVersion();
        /*用户信息*/
        getUserMsg();
        /*获取提示信息*/
        geMainTips();
    }


    private void setup() {
        ArrayList<TabInfo> infos = new ArrayList<>();

        TabInfo homeTabInfo = new TabInfo(HomeFragment_.builder().build(), R.drawable.tab_home, R.string.tab_home);
        infos.add(homeTabInfo);

        TabInfo preheatTabInfo = new TabInfo(WalletFragment_.builder().build(), R.drawable.tab_wallet, R.string.tab_wallet);
        infos.add(preheatTabInfo);

        TabInfo shopingTabInfo = new TabInfo(HomeFragment_.builder().build(), R.drawable.tab_share, R.string.tab_share);
        infos.add(shopingTabInfo);

        TabInfo mineTabInfo = new TabInfo(SharebenefitFragment_.builder().build(), R.drawable.tab_profit, R.string.tab_profit);
        infos.add(mineTabInfo);

        TabInfo mineTabInfos = new TabInfo(MineFragment_.builder().build(), R.drawable.tab_mine, R.string.tab_mine);
        infos.add(mineTabInfos);

        TabFragmentAdapter adapter = new TabFragmentAdapter(infos);
        mPager.setAdapter(adapter);

        for (int i = 0; i < adapter.getCount(); i++) {
            //i == 0设置为可点击
            mTab.addTab(mTab.newTab().setCustomView(createTabView(adapter.getIconResId(i), adapter.getTitleResId(i), i)), i == 0);
        }

        mTab.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mPager));
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTab.getTabAt(position).select();
            }
        });

    }

    private View createTabView(int iconResId, int titelResId, int i) {
        View view = getLayoutInflater().inflate(R.layout.item_home, null);
        ((ImageView) view.findViewById(R.id.homeIcon)).setImageResource(iconResId);
        ((TextView) view.findViewById(R.id.homeTitle)).setText(titelResId);
        view.findViewById(R.id.imageView29).setVisibility(View.GONE);
        return view;
    }

    private class TabFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {

        private ArrayList<TabInfo> tabInfos;

        public TabFragmentAdapter(ArrayList<TabInfo> tabInfos) {
            super(getSupportFragmentManager());
            this.tabInfos = tabInfos;
        }

        @Override
        public int getIconResId(int index) {
            return tabInfos.get(index).iconResId;
        }

        public int getTitleResId(int index) {
            return tabInfos.get(index).titleResId;
        }

        @Override
        public int getCount() {
            return tabInfos.size();
        }


        @Override
        public Fragment getItem(int position) {
            return tabInfos.get(position).fragment;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //取消销毁fragment,提高性能
            //super.destroyItem(container, position, object);
        }
    }

    private class TabInfo {
        private Fragment fragment;
        private int iconResId;
        private int titleResId;

        public TabInfo(Fragment fragment, int resId, int titleResId) {
            this.fragment = fragment;
            this.iconResId = resId;
            this.titleResId = titleResId;
        }
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0){
            moveTaskToBack(true);
        }else{
            super.onBackPressed();
        }
    }

    public void setPageCurturPage(int page){
        mPager.setCurrentItem(page);
    }


    /*开启倒计时，当为8分钟的时候自动刷新token*/
    public void restart() {
        timer.start();
    }

    private CountDownTimer timer = new CountDownTimer(480000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            //刷新数据
            refreshlogin();
        }
    };

    //刷新数据
    private void refreshlogin() {
        Subscriber subscriber = new PosetSubscriber<LoginResponse>().getSubscriber(callback_refhresh);
        UserManager.refreshLogin(subscriber);
    }

    /*获取版本号*/
    private void getVersion() {
        Subscriber subscriber = new PosetSubscriber<VersionResponst>().getSubscriber(callback_getversion);
        UserManager.getVersion(subscriber);
    }

    //刷新数据回调
    ResponseResultListener callback_refhresh = new ResponseResultListener<LoginResponse>() {
        @Override
        public void success(LoginResponse returnMsg) {
            if (returnMsg == null || TextUtils.isEmpty(returnMsg.getAccess_token())){
                EventBus.getDefault().post(new ActivityFinish(true));
                Intent intent = new Intent(MainActivity.this, LoginActivity_.class);
                startActivity(intent);
                finish();
            }else{
                SecurePreferences.getInstance().edit().putString("Authorization", returnMsg.getAccess_token()).commit();
                SecurePreferences.getInstance().edit().putString("EXPIRESDATE", returnMsg.getExpires_date()).commit();
                restart();
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            EventBus.getDefault().post(new ActivityFinish(true));
            Intent intent = new Intent(MainActivity.this, LoginActivity_.class);
            startActivity(intent);
            finish();
        }
    };

    ResponseResultListener callback_getversion = new ResponseResultListener<VersionResponst>() {
        @Override
        public void success(VersionResponst returnMsg) {
            SecurePreferences.getInstance().edit().putInt("REGIONVERSION", returnMsg.getRegion_version()).commit();
            SecurePreferences.getInstance().edit().putString("SERVERPHONE", returnMsg.getService_phone()).commit();
            SecurePreferences.getInstance().edit().putString("MINTRANSFER", returnMsg.getMin_transfer()).commit();

            try {
                PackageManager pm = getPackageManager();
                PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
                int curtentVersion = packageInfo.versionCode;
                int serverVersion = returnMsg.getAndroid_version_num();
                if (curtentVersion < serverVersion) {
                    //判断是否需要强制升级
                    if (returnMsg.is_force_update()) {
                        //直接升级
                        UpdateCorrect(returnMsg);
                    } else {
                        //提示升级
                        AlertUpdate(returnMsg);
                    }

                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };

    @Subscribe
    public void finishActivity(ActivityFinish listener) {
        if (listener.isfinish) {
            JPushInterface.stopPush(getActivity());
            SecurePreferences.getInstance().edit().putString("Authorization", "").commit();
            SecurePreferences.getInstance().edit().putString("EXPIRESDATE", "").commit();
            SecurePreferences.getInstance().edit().putString("USERQRCODE", "").commit(); //二维码
            SecurePreferences.getInstance().edit().putString("USERRRECOMMENDER", "").commit();//推荐人
            SecurePreferences.getInstance().edit().putInt("USERID", 0).commit();//商户id
            SecurePreferences.getInstance().edit().putInt("USERAUTHSTATUS", 0).commit();//认证状态：0未认证，1待认证，2未通过，3已认证
            SecurePreferences.getInstance().edit().putInt("USERGRADEID", 0).commit();//当前等级数
            SecurePreferences.getInstance().edit().putString("USERNAME", "").commit();//姓名
            SecurePreferences.getInstance().edit().putString("USERPHOTO", "").commit();//头像
            SecurePreferences.getInstance().edit().putString("USERAUTHSTATUSNAME", "").commit();//认证状态
            SecurePreferences.getInstance().edit().putString("USERGRADENAME", "").commit();//等级
            SecurePreferences.getInstance().edit().putInt("USERPARENT", 0).commit();//推荐人ID
            SecurePreferences.getInstance().edit().putInt("USERGRADECOUNT", 0).commit();//等级总数
            SecurePreferences.getInstance().edit().putBoolean("DERECTLOGIN", false).commit();
        }
    }



    private UploadAlertDialog myAlertDialog;

    private void AlertUpdate(final VersionResponst returnMsg) {
        myAlertDialog = new UploadAlertDialog(MainActivity.this, true);
        myAlertDialog.show();
        myAlertDialog.setCancelable(false);
        myAlertDialog.setContent(Html.fromHtml(returnMsg.getAndroid_update_content()));
        myAlertDialog.setLeftText("立即升级");
        myAlertDialog.setRightText("以后再说");
        myAlertDialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新
                UpdateNow(returnMsg.getAndroid_update_url(),  returnMsg.getFile_size());
            }
        });
    }

    private void UpdateCorrect(final VersionResponst returnMsg) {
        myAlertDialog = new UploadAlertDialog(MainActivity.this, false);
        myAlertDialog.show();
        myAlertDialog.setCancelable(false);
        myAlertDialog.setContent(Html.fromHtml(returnMsg.getAndroid_update_content()));
        myAlertDialog.setLeftText("立即升级");
        myAlertDialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新
                UpdateNow(returnMsg.getAndroid_update_url(), returnMsg.getFile_size());
            }
        });
    }

    /*开始升级*/
    public void UpdateNow(final String downloadUrl, final int packageSize) {
        /*测试地址*/
//        downloadUrl = "http://orzrcdvjo.bkt.clouddn.com/XGW.apk";
        //下载需要写SD卡权限, targetSdkVersion>=23 需要动态申请权限
        RxPermissions.getInstance(MainActivity.this)
                // 申请权限
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if(granted){
                            //请求成功
                            ToastUtil.showToast("正在下载，请稍后...");
                            if (!isdownLoad){
                                isdownLoad = true;
                                //开始升级
                                downloadTask = new DownloadTask(packageSize, downLoadListener);
                                downloadTask.execute(downloadUrl);
                            }
                        }else{
                            // 请求失败回收当前服务
                            getVersion();
                        }
                    }
                });

    }

    /*下载更新包回调*/
    DownLoadListener downLoadListener = new DownLoadListener() {
        @Override
        public void finishNotify() {
            isdownLoad = false;
            finishNotifyV();
        }

        @Override
        public void shownotifi() {
            shownotifiV();
        }

        @Override
        public void installApk() {
            installApkV();
        }

        @Override
        public void refreshView(int filelen, int nowlenth) {
            myAlertDialog.setProgress(nowlenth * 100/filelen);

            views.setProgressBar(R.id.progressBar1, filelen, nowlenth, false);
            views.setTextViewText(R.id.textView1, nowlenth * 100/filelen + "%");
            mNotification.notify(123, build);
        }

        @Override
        public void dismisNotification() {
            build.flags = Notification.FLAG_AUTO_CANCEL;
        }

        @Override
        public void cancleNotification() {
            mNotification.cancel(123);
        }
    };


    private NotificationManager mNotification;
    private RemoteViews views;
    private Notification build;


    public void finishNotifyV() {
        views.setViewVisibility(R.id.progressBar1, View.INVISIBLE);
        views.setTextViewText(R.id.textView1, "下载完成,点击升级");
        views.setTextViewText(R.id.textView2, "");
        mNotification.cancel(123);
    }

    public void shownotifiV() {
        mNotification = (NotificationManager) MyApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
        views = new RemoteViews(MyApplication.getInstance().getPackageName(), R.layout.notification);
        Intent intent = new Intent();
        PendingIntent ic = PendingIntent.getActivity(this, 0, intent, 0);
        build = new NotificationCompat.Builder(this)
                .setContent(views)
                .setContentTitle("升级")
                .setTicker("开始升级")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(ic)
                .build();
        if (Build.VERSION.SDK_INT <= 10) {
            build.contentView = views;
        }
        mNotification.notify(123, build);
    }

    /**
     * 安装APK文件
     */
    private void installApkV() {
        File apkfile = new File(Constant.SAVEAPPFILEPATH);
        if (!apkfile.exists()) {
            return;
        }
//        // 通过Intent安装APK文件
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
//                "application/vnd.android.package-archive");
//        startActivity(i);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri uri = FileProvider.getUriForFile(MainActivity.this, "com.shidai.yunshang", apkfile);    //第二个参数是manifest中定义的`authorities`
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Subscribe
    public void refreshData(RefreshListener refreshListener){
        if (refreshListener.tag.equals("finishFragment") && refreshListener.refresh){
            getUserMsg();
        }
    }

    private void getUserMsg() {
        Subscriber subscriber = new PosetSubscriber<UsermsgResponse>().getSubscriber(callback_usremsg);
        UserManager.getUsermsg(subscriber);
    }


    private void geMainTips(){
        Subscriber subscriber = new PosetSubscriber<List<TipsMsgResponse>>().getSubscriber(callback_listtips);
        UserManager.getTips(subscriber);
    }

    ResponseResultListener callback_usremsg = new ResponseResultListener<UsermsgResponse>() {
        @Override
        public void success(UsermsgResponse returnMsg) {
            /*设置别名*/
            setAlias(returnMsg.getMobile());
            SecurePreferences.getInstance().edit().putString("USERQRCODE", returnMsg.getQrcode()).commit(); //二维码
            SecurePreferences.getInstance().edit().putString("USERRRECOMMENDER", returnMsg.getRecommender()).commit();//推荐人
            SecurePreferences.getInstance().edit().putInt("USERID", returnMsg.getId()).commit();//商户id
            SecurePreferences.getInstance().edit().putInt("USERAUTHSTATUS", returnMsg.getAuth_status()).commit();//认证状态：0未认证，1待认证，2未通过，3已认证
            SecurePreferences.getInstance().edit().putInt("USERGRADEID", returnMsg.getGrade_id()).commit();//当前等级数
            SecurePreferences.getInstance().edit().putString("USERNAME", returnMsg.getName()).commit();//姓名
            SecurePreferences.getInstance().edit().putString("USERPHOTO", returnMsg.getPhoto()).commit();//头像
            SecurePreferences.getInstance().edit().putString("USERAUTHSTATUSNAME", returnMsg.getAuth_status_name()).commit();//认证状态
            SecurePreferences.getInstance().edit().putString("USERGRADENAME", returnMsg.getGrade_name()).commit();//等级
            SecurePreferences.getInstance().edit().putInt("USERPARENT", returnMsg.getParent_id()).commit();//推荐人ID
            SecurePreferences.getInstance().edit().putInt("USERGRADECOUNT", returnMsg.getGrade_count()).commit();//等级总数
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };


    /*获取消息*/
    ResponseResultListener callback_listtips = new ResponseResultListener<List<TipsMsgResponse>>() {
        @Override
        public void success(List<TipsMsgResponse> returnMsg) {
            if (returnMsg.size()>0){
                position = 0;
                showTips(returnMsg);
            }
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };

    private MyAlertDialog mAlertDialog;
    private int position = 0;

    private void showTips(final List<TipsMsgResponse> returnMsg) {
        if (returnMsg.size() > position){
            final String positive, negtive;
            final TipsMsgResponse response = returnMsg.get(position);
            if (response.getName().equals("auth")){
                positive = "前往完善";
                negtive = "取消";
            }else{
                positive = "立即升级";
                negtive = "取消";
            }
            mAlertDialog = new MyAlertDialog(getActivity(), true);
            mAlertDialog.show();
            mAlertDialog.setTitle(response.getTitle());
            mAlertDialog.setContent(Html.fromHtml(response.getContent()));
            mAlertDialog.setLeftText(positive);//确认
            mAlertDialog.setRightText(negtive);
            mAlertDialog.setOnPositiveListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (response.getName().equals("auth")){
                        //前往完善
                        ToastUtil.showToast("完善认证");
                    }else{
                        //立即升级
                        showFragment(AuthorizationFragment_.builder().build());
                    }
                    mAlertDialog.dismiss();
                }
            });
            mAlertDialog.setOnNegsitiveListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position++;
                    showTips(returnMsg);
                    mAlertDialog.dismiss();
                }
            });
        }
    }

    //极光推送的设置
    /*设置别名*/
    private void setAlias(String mobile) {
        if (!Utils.isValidTagAndAlias(mobile)) {
            LogUtil.E("别名设置", "error_tag_gs_empty");
            return;
        }

        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, mobile));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    /*别名设置成功*/
                    logs = "Set tag and alias success";
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    /*别名设置失败*/
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
            }
        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;

                default:
            }
        }
    };


}
