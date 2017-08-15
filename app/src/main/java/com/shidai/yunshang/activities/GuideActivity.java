package com.shidai.yunshang.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.activities.base.BaseActivity;
import com.shidai.yunshang.adapters.GuidePageAdapter;
import com.shidai.yunshang.utils.ImageLoader;
import com.shidai.yunshang.utils.SecurePreferences;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/29 14:45
 **/
@EActivity(R.layout.activity_guide)
public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    private ViewPager vp;

//    private int []imageIdArray;//图片资源的数组
    private List<ImageView> viewList;//图片资源的集合
    private ViewGroup vg;//放置圆点

    //最后一页的按钮
    @ViewById(R.id.guide_ib_start)
    ImageButton ib_start;

    @ViewById(R.id.imaFlash)
     ImageView imaFlash;

    private List<Integer> listPic = new ArrayList<>();
    private GuidePageAdapter guideAdapter;
    /*是否发生跳转*/
    private boolean isIntent = false;

    private int recLen = 10;

    @AfterViews
    void initView(){
        listPic.add(R.drawable.loading1); listPic.add(R.drawable.loading2); listPic.add(R.drawable.loading3);

        new FlashAsynTask().execute("");

        ib_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isIntent){
                    isIntent = true;
                    startActivity(new Intent(GuideActivity.this, LoginActivity_.class));
                    finish();
                }
            }
        });

//        //加载底部圆点
//        initPoint();

//        txtTimeCount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!isIntent){
//                    isIntent = true;
//                    startActivity(new Intent(GuideActivity.this, SelectLikeTypeActivity_.class));
//                    finish();
//                }
//            }
//        });
    }


    class FlashAsynTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!SecurePreferences.getInstance().getBoolean("FIRSTLOGIN", false)){
                //加载ViewPager
                initViewPager();
                imaFlash.setVisibility(View.GONE);
            }else{
                if (!isIntent){
                    isIntent = true;
                    startActivity(new Intent(GuideActivity.this, LoginActivity_.class));
                }
                finish();
            }
        }
    }


    final Handler handler = new Handler(){

        public void handleMessage(Message msg){         // handle message
            switch (msg.what) {
                case 1:
                    recLen--;
                    if(recLen > 0){
                        Message message = handler.obtainMessage(1);
                        handler.sendMessageDelayed(message, 1000);      // send message
                    }else{
                        if (!isIntent){
                            isIntent = true;
                            startActivity(new Intent(GuideActivity.this, LoginActivity_.class));
                            finish();
                        }
                    }
            }

            super.handleMessage(msg);
        }
    };



    /**
     * 加载图片ViewPager
     */
    private void initViewPager() {
        vp = (ViewPager) findViewById(R.id.guide_vp);
        vp.setOffscreenPageLimit(4);
        //实例化图片资源
//        imageIdArray = new int[]{R.drawable.guide1,R.drawable.guide2,R.drawable.guide3,R.drawable.guide4};
        viewList = new ArrayList<>();
        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

        //循环创建View并加入到集合中
        int len = listPic.size();
        for (int i = 0;i<len;i++){
            //new ImageView并设置全屏和图片资源
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(listPic.get(i));
            //将ImageView加入到集合中
            viewList.add(imageView);
        }

        //View集合初始化好后，设置Adapter
        vp.setAdapter(guideAdapter = new GuidePageAdapter(viewList));
        //设置滑动监听
        vp.setOnPageChangeListener(this);
        //倒计时
        Message message = handler.obtainMessage(1);     // Message
        handler.sendMessageDelayed(message, 1000);
    }


//    /**
//     * 加载底部圆点
//     */
//    private void initPoint() {
//        //这里实例化LinearLayout
//        vg = (ViewGroup) findViewById(R.id.guide_ll_point);
//        //根据ViewPager的item数量实例化数组
//        ivPointArray = new ImageView[viewList.size()];
//        //循环新建底部圆点ImageView，将生成的ImageView保存到数组中
//        int size = viewList.size();
//        for (int i = 0; i < size; i++) {
//            iv_point = new ImageView(this);
//            iv_point.setLayoutParams(new ViewGroup.LayoutParams(20, 20));
//            iv_point.setPadding(30, 0, 30, 0);//left,top,right,bottom
//            ivPointArray[i] = iv_point;
//            //第一个页面需要设置为选中状态，这里采用两张不同的图片
//            if (i == 0) {
//                iv_point.setBackgroundResource(R.drawable.ic_check_round);
//            } else {
//                iv_point.setBackgroundResource(R.drawable.ic_unselected_round);
//            }
//            //将数组中的ImageView加入到ViewGroup
//            vg.addView(ivPointArray[i]);
//        }
//    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 滑动后的监听
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        //循环设置当前页的标记图
//        int length = imageIdArray.length;
//        for (int i = 0;i<length;i++){
//            ivPointArray[position].setBackgroundResource(R.drawable.ic_check_round);
//            if (position != i){
//                ivPointArray[i].setBackgroundResource(R.drawable.ic_unselected_round);
//            }
//        }

        //判断是否是最后一页，若是则显示按钮
        if (position == listPic.size() - 1){
            ib_start.setVisibility(View.VISIBLE);
        }else {
            ib_start.setVisibility(View.GONE);
        }
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
         /*第一次登录*/
        SecurePreferences.getInstance().edit().putBoolean("FIRSTLOGIN", true).commit();
    }
}