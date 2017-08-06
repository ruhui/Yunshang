package com.shidai.yunshang.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.models.BillbagModel;
import com.shidai.yunshang.utils.ImageLoader;
import com.shidai.yunshang.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/8/6.
 * 作者：黄如辉
 * 功能描述：
 */
@EViewGroup(R.layout.adapter_wallettitle)
public class WalletTitleViewHold extends LinearLayout {

    @ViewById(R.id.imgZFB)
    ImageView imgZFB;
    @ViewById(R.id.textView32)
    TextView txtTitle;
    @ViewById(R.id.line2)
    View viewLine;

    private Context mContext;

    public WalletTitleViewHold(Context context) {
        super(context);
        mContext = context;
    }

    public WalletTitleViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void bind(BillbagModel model, int position){
//        ImageLoader.loadImage(Tool.getPicUrl(mContext, model.get));
        txtTitle.setText(model.getName());
        if (model.isClick()){
            viewLine.setVisibility(VISIBLE);
        }else{
            viewLine.setVisibility(INVISIBLE);
        }
    }
}
