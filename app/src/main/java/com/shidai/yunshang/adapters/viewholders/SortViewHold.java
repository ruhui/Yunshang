package com.shidai.yunshang.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.networks.responses.SortResponse;
import com.shidai.yunshang.utils.ImageLoader;
import com.shidai.yunshang.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/7/29.
 * 作者：黄如辉
 * 功能描述：
 */
@EViewGroup(R.layout.adapter_sort)
public class SortViewHold extends LinearLayout {

    @ViewById(R.id.textView46)
    TextView txtCount;
    @ViewById(R.id.imgHead)
    ImageView imgHead;
    @ViewById(R.id.textView47)
    TextView txtName;
    @ViewById(R.id.textView45)
    TextView txtMoney;
    @ViewById(R.id.txtDes)
    TextView txtDes;

    private Context mContext;

    public SortViewHold(Context context) {
        super(context);
        mContext = context;
    }

    public SortViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void bind(SortResponse sortResponse, int position, String cardType){
        String userName = sortResponse.getName();
        txtCount.setText(position+1 +"");
        ImageLoader.loadImage(Tool.getPicUrl(mContext, sortResponse.getPhoto(), 44, 44), imgHead);
        txtMoney.setText("¥"+sortResponse.getProfit());
        if (cardType.equals("1")){
            //好友榜
            txtDes.setVisibility(VISIBLE);
            txtName.setText(userName);
        }else{
            //江湖榜
            txtDes.setVisibility(GONE);
            if (!TextUtils.isEmpty(userName)){
                userName = userName.substring(0, 1);
            }
            txtName.setText(userName + "**");
//            if (position < 2){
//                txtMoney.setText("¥"+sortResponse.getProfit());
//            }else{
//                String moneyStr = Tool.formatPrice(sortResponse.getProfit());
//                moneyStr.
//            }
        }

    }
}
