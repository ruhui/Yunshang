package com.shidai.yunshang.adapters.viewholders;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shidai.yunshang.models.ChannelModel;
import com.shidai.yunshang.models.SuerPayModel;
import com.shidai.yunshang.R;
import com.shidai.yunshang.utils.Tool;

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
    @ViewById(R.id.imageView4)
    ImageView imgCardIcon;


    public SureOrderViewHold(Context context) {
        super(context);
    }

    public SureOrderViewHold(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(ChannelModel model){
        imgCardIcon.setImageResource(R.drawable.qb_ylzf);
        String single_quota;//单笔额度
        if (model.getSingle_quota() == 0){
            single_quota = "不限";
        }else{
            single_quota = Tool.formatPrice(model.getSingle_quota()) + "万/笔";
        }

        String card_quota = "";//单卡额度/天,0不限
        if (model.getCard_quota() == 0){
            card_quota = "不限";
        }else{
            single_quota = Tool.formatPrice(model.getCard_quota()) + "万/天";
        }

        String fee ;//费率
        fee = Tool.formatPrice(model.getFee() * 100) + "%";

        String settle ;//结算费
        settle = Tool.formatPrice(model.getSettle());

        txtTitle.setText(model.getName()  + "    费率:" +fee + "    结算费: ¥"+settle);
        txtContent.setText("单笔:" + single_quota +"  单日:"+ card_quota);
    }
}
