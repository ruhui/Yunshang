package com.shidai.yunshang.activities;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.activities.base.BaseActivity;
import com.shidai.yunshang.fragments.HomeFragment_;
import com.shidai.yunshang.fragments.MineFragment_;
import com.shidai.yunshang.fragments.SharebenefitFragment_;
import com.shidai.yunshang.fragments.WalletFragment_;
import com.shidai.yunshang.intefaces.AcitivtyFinishListener;
import com.shidai.yunshang.intefaces.ActivityFinish;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.LoginResponse;
import com.shidai.yunshang.networks.responses.VersionResponst;
import com.shidai.yunshang.utils.SecurePreferences;
import com.shidai.yunshang.view.widget.NoScrollViewPager;
import com.viewpagerindicator.IconPagerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import rx.Subscriber;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewById(R.id.tab)
    TabLayout mTab;

    @ViewById(R.id.pager)
    NoScrollViewPager mPager;


    @AfterViews
    void initView(){
        setup();
        /*开启倒计时*/
        restart();
        /*获取版本号*/
        getVersion();
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
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };

    @Subscribe
    public void finishActivity(ActivityFinish listener) {
        if (listener.isfinish) {
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
        }
    }
}
