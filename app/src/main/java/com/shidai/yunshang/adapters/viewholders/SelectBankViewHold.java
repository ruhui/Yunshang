package com.shidai.yunshang.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.models.SelectBankCardModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/7/22.
 * 作者：黄如辉
 * 功能描述：
 */
@EViewGroup(R.layout.adapter_select_bankcard)
public class SelectBankViewHold extends LinearLayout {

    @ViewById(R.id.txtCardNum)
    TextView txtCardNum;
    @ViewById(R.id.imageView6)
    ImageView imgCardIcon;


    public SelectBankViewHold(Context context) {
        super(context);
    }

    public SelectBankViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(SelectBankCardModel model){
        txtCardNum.setText(model.getCardNum());
    }
}
