package com.shidai.yunshang.adapters.viewholders;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shidai.yunshang.models.SuerPayModel;
import com.shidai.yunshang.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/7/21 11:27
 **/
@EViewGroup(R.layout.adapter_surepay)
public class SureOrderViewHold extends LinearLayout {

    @ViewById(R.id.textView10)
    TextView txtTitle;
    @ViewById(R.id.textView11)
    TextView txtContent;


    public SureOrderViewHold(Context context) {
        super(context);
    }

    public SureOrderViewHold(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(SuerPayModel model){
        txtTitle.setText(model.getTitle());
        txtContent.setText(model.getContent());
    }
}
