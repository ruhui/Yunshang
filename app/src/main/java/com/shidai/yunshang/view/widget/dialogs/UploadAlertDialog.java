package com.shidai.yunshang.view.widget.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.view.widget.MSeekBar;

/**
    * 得先show，否则会出空指针
    * 创建作者： 黄如辉
    * 创建时间： 2017/4/22 14:25
   **/

public class UploadAlertDialog extends AlertDialog {

    public TextView dialogTitle, btnLeft, btnRight;
    public RelativeLayout relaSure,relaCancle;
    private LinearLayout linearLayout2;
    private MSeekBar mSeekBar;
    private boolean bothbutton = false;
     private Context mContext;
    private TextView txtContent;


    public UploadAlertDialog(Context context, boolean bothbutton) {
        super(context,  R.style.MyDialogTheme2);
        this.bothbutton = bothbutton;
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update_alert);
        dialogTitle = (TextView) findViewById(R.id.textView59);
        txtContent = (TextView) findViewById(R.id.textView60);
        relaSure = (RelativeLayout) findViewById(R.id.relaSure);
        relaCancle = (RelativeLayout) findViewById(R.id.relaCancle);
        btnLeft = (TextView) findViewById(R.id.textView61);
        btnRight = (TextView) findViewById(R.id.btnCancel);
        mSeekBar = (MSeekBar) findViewById(R.id.mSeekbar);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        mSeekBar.setVisibility(View.GONE);

        if (bothbutton){
            relaCancle.setVisibility(View.VISIBLE);
        }
        relaCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public void setMaxProgress(int max){
        txtContent.setVisibility(View.GONE);
        mSeekBar.setVisibility(View.VISIBLE);
        mSeekBar.setMax(max);
    }

    public void setProgress(int progress){
        dialogTitle.setText("正在升级");
        dialogTitle.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        dialogTitle.setGravity(Gravity.CENTER);
        txtContent.setVisibility(View.GONE);
        mSeekBar.setVisibility(View.VISIBLE);
        linearLayout2.setVisibility(View.GONE);
        mSeekBar.setProgress(progress);
    }

    public void setSeekbarScroll(){
        mSeekBar.setScroller();
    }

    public void setTitle(String title){
        dialogTitle.setText(title);
    }

    public void setContent(String content){
//        txtContent.setText(content);
//        showWebView(content);
    }

     public void setContent(Spanned content){
         txtContent.setText(content);
         txtContent.setMovementMethod(ScrollingMovementMethod.getInstance());
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

//    private void showWebView(String html)
//    {
//        // 设置WevView要显示的网页
//        txtContent.loadDataWithBaseURL(null, html, "text/html", "utf-8",
//                null);
//        txtContent.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        txtContent.getSettings().setJavaScriptEnabled(true); //设置支持Javascript
//        //luntanListview.requestFocus(); //触摸焦点起作用.如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。
//        //        luntanListview.getSettings().setBuiltInZoomControls(true); //页面添加缩放按钮
//        //                luntanListview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);   //取消滚动条
//
//        //                点击链接由自己处理，而不是新开Android的系统browser响应该链接。
//    }
}
