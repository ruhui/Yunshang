package com.shidai.yunshang.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.models.BillbagModel;
import com.shidai.yunshang.models.ChannelModel;
import com.shidai.yunshang.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/7/28.
 * 作者：黄如辉
 * 功能描述：
 */
@EViewGroup(R.layout.adapter_wallet)
public class WalletViewHold extends LinearLayout {

    @ViewById(R.id.imgCardicon)
    ImageView imgCardIcon;
    @ViewById(R.id.txtName)
    TextView txtName;
    @ViewById(R.id.txtContent)
    TextView txtContent;

    public WalletViewHold(Context context) {
        super(context);
    }

    public WalletViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(String payType, ChannelModel model){
        if (payType.equals("ALIPAY")){
            //支付宝
            imgCardIcon.setImageResource(R.drawable.qb_zfb);
        }else if (payType.equals("WXPAY")){
            //微信
            imgCardIcon.setImageResource(R.drawable.qb_wxzf);
        }else if (payType.equals("UNIONPAY")){
            //银联支付
            imgCardIcon.setImageResource(R.drawable.qb_ylzf);
        }

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
        settle = Tool.formatPrice(model.getSettle())+"元";

        txtName.setText(model.getName());
        txtContent.setText("额度:" + single_quota +"  "+ card_quota + "    费率:" +fee + "    结算费:"+settle);
    }
}
