package com.shidai.yunshang.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shidai.yunshang.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/7/19.
 * 作者：黄如辉
 * 功能描述：
 */

@EViewGroup(R.layout.view_pictext_45)
public class PicTextView45 extends LinearLayout {

    @ViewById(R.id.imageView3)
    ImageView imageView3;
    @ViewById(R.id.textView)
    TextView textView;

    public PicTextView45(Context context) {
        super(context);
    }

    public PicTextView45(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setImageResource(int imageResource){
        imageView3.setImageResource(imageResource);
    }

    public void setTextView(int strTxt){
        textView.setText(strTxt);
    }

    public void setTextView(String strTxt){
        textView.setText(strTxt);
    }
}
