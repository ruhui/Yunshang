package com.shidai.yunshang.view.widget.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shidai.yunshang.R;

/**
    * 得先show，否则会出空指针
    * 创建作者： 黄如辉
    * 创建时间： 2017/4/22 14:25
   **/

public class MyAlertDialog extends AlertDialog {

    public TextView dialogTitle,txtContent, btnLeft, btnRight;
    public RelativeLayout relaSure,relaCancle;
    private boolean bothbutton = false;
     private Context mContext;


    public MyAlertDialog(Context context, boolean bothbutton) {
        super(context,  R.style.MyDialogTheme2);
        this.bothbutton = bothbutton;
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_alert);
        dialogTitle = (TextView) findViewById(R.id.textView59);
        txtContent = (TextView) findViewById(R.id.textView60);
        relaSure = (RelativeLayout) findViewById(R.id.relaSure);
        relaCancle = (RelativeLayout) findViewById(R.id.relaCancle);
        btnLeft = (TextView) findViewById(R.id.textView61);
        btnRight = (TextView) findViewById(R.id.btnCancel);

        if (bothbutton){
            relaCancle.setVisibility(View.VISIBLE);
        }

    }

    public void setTitle(String title){
        dialogTitle.setText(title);
    }

    public void setContent(String content){
        txtContent.setText(content);
    }

     public void setContent(Spanned content){
         txtContent.setText(content);
         txtContent.setMovementMethod(ScrollingMovementMethod.getInstance());
     }

    public void setOnNegsitiveListener(View.OnClickListener listener){
        relaCancle.setOnClickListener(listener);
    }

    public void setOnPositiveListener(View.OnClickListener listener){
        relaSure.setOnClickListener(listener);
    }

     public void setLeftText(String title){
         btnLeft.setText(title);
     }

     public void setRightText(String title){
         btnRight.setText(title);
     }

     public void setLeftColor(int color){
         btnLeft.setTextColor(mContext.getResources().getColor(color));
     }

     public void setRightColor(int color){
         btnRight.setTextColor(mContext.getResources().getColor(color));
     }

     public void setTopTitlColor(int color){
         dialogTitle.setTextColor(mContext.getResources().getColor(color));
     }
}
