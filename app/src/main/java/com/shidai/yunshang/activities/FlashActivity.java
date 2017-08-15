package com.shidai.yunshang.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shidai.yunshang.R;
import com.shidai.yunshang.activities.base.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import java.io.Serializable;

import rx.Subscriber;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/27 14:03
 **/
@EActivity(R.layout.activity_flash)
public class FlashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }
    }

    @AfterViews
    void initView(){
        Intent intent = new Intent(FlashActivity.this, GuideActivity_.class);
        startActivity(intent);
        finish();
        //new FlashAsynTask().execute();
    }

    class FlashAsynTask extends AsyncTask<String, Void, String>{

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

            Intent intent = new Intent(FlashActivity.this, LoginActivity_.class);
            startActivity(intent);
            finish();
        }
    }

}
