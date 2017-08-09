package com.shidai.yunshang.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.activities.PersonMessageActivity_;
import com.shidai.yunshang.activities.WebActivity_;
import com.shidai.yunshang.fragments.base.BaseFragment;
import com.shidai.yunshang.intefaces.RefreshListener;
import com.shidai.yunshang.intefaces.ResponseResultListener;
import com.shidai.yunshang.managers.UrlAddressManger;
import com.shidai.yunshang.managers.UserManager;
import com.shidai.yunshang.networks.PosetSubscriber;
import com.shidai.yunshang.networks.responses.UsermsgResponse;
import com.shidai.yunshang.utils.ImageLoader;
import com.shidai.yunshang.utils.SecurePreferences;
import com.shidai.yunshang.utils.Tool;
import com.shidai.yunshang.view.widget.ItemView1;
import com.shidai.yunshang.view.widget.MyscrollerView;
import com.shidai.yunshang.view.widget.NavBarBack;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import rx.Subscriber;

/**
 * 创建时间： 2017/7/22.
 * 作者：黄如辉
 * 功能描述：
 */
@EFragment(R.layout.fragment_mine)
public class MineFragment extends BaseFragment implements MyscrollerView.ScrollerListeners {

    @ViewById(R.id.itemView1)
    ItemView1 itemView1;
    @ViewById(R.id.itemView2)
    ItemView1 itemView2;
    @ViewById(R.id.itemView3)
    ItemView1 itemView3;
    @ViewById(R.id.itemView4)
    ItemView1 itemView4;
    @ViewById(R.id.itemView5)
    ItemView1 itemView5;
    @ViewById(R.id.itemView6)
    ItemView1 itemView6;
    @ViewById(R.id.itemView7)
    ItemView1 itemView7;
    @ViewById(R.id.itemView8)
    ItemView1 itemView8;
    @ViewById(R.id.mNavbar)
    NavBarBack mNavbar;
    @ViewById(R.id.scrollView)
    MyscrollerView myScrollView;

    /*头像*/
    @ViewById(R.id.imageView8)
    ImageView imgHeadView;
    /*姓名*/
    @ViewById(R.id.textView12)
    TextView txtName;
    /*账号*/
    @ViewById(R.id.textView13)
    TextView txtPhone;
    /*等级*/
    @ViewById(R.id.textView14)
    TextView txtGride;
    @ViewById(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @AfterViews
    void initView(){
        setAlpha(0);
        myScrollView.MyscrollerView(this);
        mNavbar.setMiddleTitle("我的");
        mNavbar.setDisplayLeftMenu(false);

        itemView1.setLeftIcon(R.drawable.wd_tjr);itemView1.setMiddelTxt("推荐人");
        itemView2.setLeftIcon(R.drawable.wd_smrz);itemView2.setMiddelTxt("实名认证");
        itemView3.setLeftIcon(R.drawable.wd_yhkgl);itemView3.setMiddelTxt("银行卡管理");
        itemView4.setLeftIcon(R.drawable.wd_xxtz);itemView4.setMiddelTxt("消息通知");
        itemView5.setLeftIcon(R.drawable.wd_dd);itemView5.setMiddelTxt("我的订单");itemView5.setVisibility(View.GONE);
        itemView6.setLeftIcon(R.drawable.wd_shdz);itemView6.setMiddelTxt("收货地址");itemView6.setVisibility(View.GONE);
        itemView7.setLeftIcon(R.drawable.wd_kf);itemView7.setMiddelTxt("QQ客服");
        itemView8.setLeftIcon(R.drawable.wd_gd);itemView8.setMiddelTxt("更多");itemView8.setLineVisiable(false);

        /*个人资料*/
        imgHeadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonMessageActivity_.class);
                startActivity(intent);
            }
        });

        /*推荐人*/
        itemView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int parentId = SecurePreferences.getInstance().getInt("USERPARENT", 0);//推荐人ID
                if (parentId == 0){
                    //添加推荐人
                    showFragment(getActivity(), RecommendFragment_.builder().build());
                }else{
                    //推荐人详情
                    ErweimaFragment fragment = ErweimaFragment_.builder().build();
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "2");
                    fragment.setArguments(bundle);
                    showFragment(getActivity(), fragment);
                }
            }
        });

        /*消息通知*/
        itemView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(getActivity(), MineMsgFragment_.builder().build());
            }
        });

        /*银行卡管理*/
        itemView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(getActivity(), MyBankCardFragment_.builder().build());
            }
        });

        /*客服*/
        itemView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebActivity_.class);
                intent.putExtra("titleBar", "QQ客服");
                intent.putExtra("webUrl", UrlAddressManger.CUSTOMSERVICE);
                startActivity(intent);
            }
        });

        /*更多*/
        itemView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(getActivity(), MoreFragment_.builder().build());
            }
        });
    }


    /*晋升*/
    @Click(R.id.imgjs)
    void update(){
        showFragment(getActivity(), AuthorizationFragment_.builder().build());
    }

    /*二维码*/
    @Click(R.id.imgErweima)
    void erweima(){
        showFragment(getActivity(), ErweimaFragment_.builder().build());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        /*获取我的资料*/
        getUserMsg();
    }

    private void getUserMsg() {
        Subscriber subscriber = new PosetSubscriber<UsermsgResponse>().getSubscriber(callback_usremsg);
        UserManager.getUsermsg(subscriber);
    }


    @Override
    public void scroller(int scrollY) {
        int height =mNavbar.getMeasuredHeight();
        if (scrollY <= 0){
            setAlpha(0);
        }else if(scrollY >= height){
            setAlpha(1);
        }else{
            setAlpha((float) scrollY/height);
        }
    }

    public void setAlpha(float alpha){
        mNavbar.setAlpha(alpha);
    }

    ResponseResultListener callback_usremsg = new ResponseResultListener<UsermsgResponse>() {
        @Override
        public void success(UsermsgResponse returnMsg) {
            ImageLoader.loadCircleImage(Tool.getPicUrl(getActivity(), returnMsg.getPhoto(), 67, 67), imgHeadView, R.drawable.dj_yh);
            txtName.setText(returnMsg.getName());
            txtPhone.setText("账号："+returnMsg.getMobile());
            txtGride.setText(getResources().getString(R.string.shouquanzizhi)  +"  " + returnMsg.getGrade_name());
            itemView1.setRightTxt(returnMsg.getRecommender());

            progressBar.setMax(returnMsg.getGrade_count());
            progressBar.setProgress(returnMsg.getGrade_id());

            SecurePreferences.getInstance().edit().putString("USERQRCODE", returnMsg.getQrcode()).commit(); //二维码
            SecurePreferences.getInstance().edit().putString("USERRRECOMMENDER", returnMsg.getRecommender()).commit();//推荐人
            SecurePreferences.getInstance().edit().putInt("USERID", returnMsg.getId()).commit();//商户id
            SecurePreferences.getInstance().edit().putInt("USERAUTHSTATUS", returnMsg.getAuth_status()).commit();//认证状态：0未认证，1待认证，2未通过，3已认证
            SecurePreferences.getInstance().edit().putString("USERMOBILE", returnMsg.getMobile()).commit();//电话
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

    @Subscribe
    public void refreshData(RefreshListener refreshListener){
        if (refreshListener.refresh && refreshListener.tag.equals("userrefresh")){
            getUserMsg();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
