package com.shidai.yunshang.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.networks.responses.SettletypeResponse;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/8/7.
 * 作者：黄如辉
 * 功能描述：
 */
@EViewGroup(R.layout.adapter_wallettixian)
public class WalletTixianViewHolde extends LinearLayout {

    @ViewById(R.id.imageView20)
    ImageView imgLeftIcon;
    @ViewById(R.id.txtTitle)
    TextView txtTitle;

    public WalletTixianViewHolde(Context context) {
        super(context);
    }

    public WalletTixianViewHolde(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(SettletypeResponse seleResponse){
        if (seleResponse.isClick()){
            imgLeftIcon.setImageResource(R.drawable.tx_xz);
        }else{
            imgLeftIcon.setImageResource(R.drawable.tx_wxz);
        }

        txtTitle.setText(seleResponse.getName() + " 结算费 "+seleResponse.getSingle_quota());
    }
}
