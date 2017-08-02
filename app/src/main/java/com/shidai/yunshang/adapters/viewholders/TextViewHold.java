package com.shidai.yunshang.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.models.BranchbrankModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/8/2.
 * 作者：黄如辉
 * 功能描述：
 */
@EViewGroup(R.layout.adapter_text)
public class TextViewHold extends LinearLayout {

    @ViewById(R.id.txtMiddle)
    TextView txtMiddle;

    public TextViewHold(Context context) {
        super(context);
    }

    public TextViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(BranchbrankModel mode, int position){
        txtMiddle.setText(mode.getBranch_name());
    }
}
