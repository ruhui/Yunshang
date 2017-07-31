package com.shidai.yunshang.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shidai.yunshang.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/7/26.
 * 作者：黄如辉
 * 功能描述：
 */
@EViewGroup(R.layout.view_itemview3)
public class ItemView3 extends LinearLayout {

    @ViewById(R.id.textView28)
    TextView txtLeft;
    @ViewById(R.id.textView29)
    TextView edtRight;

    public ItemView3(Context context) {
        super(context);
    }

    public ItemView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTxtLeft(String leftStr){
        txtLeft.setText(leftStr);
    }

    public void setRightHint(String hintStr){
        edtRight.requestFocus();
        edtRight.setHint(hintStr);
    }

    public void setEdtRight(String rightStr){
        edtRight.setText(rightStr);
    }

    public void setGravaty(int gravaty){
        edtRight.setGravity(gravaty);
    }

    public String getRightTxt(){
        return edtRight.getText().toString();
    }
}
