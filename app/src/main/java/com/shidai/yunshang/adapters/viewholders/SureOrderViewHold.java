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

    public void bind(String pay_code, ChannelModel model){
        if (pay_code.equals("UNIONPAY")){
            //银联支付
            imgCardIcon.setImageResource(R.drawable.qrzf_yl);
        }else if(pay_code.equals("ALIPAY_JS")){

        }else if(pay_code.equals("WXPAY_JS")){

        }

        String single_quota;//额度

        if (model.getSingle_quota() == 0 && model.getCard_quota() == 0){
            single_quota = "不限";
        }else{
            single_quota = "额度:"+model.getSingle_quota()+"~"+model.getCard_quota()+"元";
        }

        String fee ;//费率
        fee = Tool.formatPrice(model.getFee() * 100) + "%";

        String settle ;//结算费
        settle = Tool.formatPrice(model.getSettle());

        txtTitle.setText(model.getName()  + "    费率:" +fee + "    结算费: ¥"+settle);
        txtContent.setText(single_quota);
    }
}
