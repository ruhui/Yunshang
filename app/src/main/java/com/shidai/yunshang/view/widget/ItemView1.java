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
 * 创建时间： 2017/7/23.
 * 作者：黄如辉
 * 功能描述：
 */
@EViewGroup(R.layout.view_itemview1)
public class ItemView1 extends LinearLayout {

    @ViewById(R.id.imageView10)
    ImageView imgLeft;
    @ViewById(R.id.textView15)
    TextView txtMiddle;
    @ViewById(R.id.textView16)
    TextView txtRight;
    @ViewById(R.id.textView17)
    TextView txtLine;

    public ItemView1(Context context) {
        super(context);
    }

    public ItemView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setLeftIcon(int drawableid){
        imgLeft.setImageResource(drawableid);
    }

    public void setMiddelTxt(String middelTxt){
        txtMiddle.setText(middelTxt);
    }

    public void setRightTxt(String rightTxt){
        txtRight.setText(rightTxt);
    }

    public void setLineVisiable(boolean visiable){
        if (visiable){
            txtLine.setVisibility(VISIBLE);
        }else{
            txtLine.setVisibility(GONE);
        }
    }
}
