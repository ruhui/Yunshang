package com.shidai.yunshang.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.models.MechantModel;
import com.shidai.yunshang.utils.ImageLoader;
import com.shidai.yunshang.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/8/7.
 * 作者：黄如辉
 * 功能描述：
 */
@EViewGroup(R.layout.adapter_mechant)
public class MechantViewHold extends LinearLayout {

    @ViewById(R.id.imageView22)
    ImageView imgHeadView;
    @ViewById(R.id.txtName)
    TextView txtName;
    /*分润贡献*/
    @ViewById(R.id.txtGxmoney)
    TextView txtGxmoney;
    @ViewById(R.id.textView64)
    TextView txtStatu;

    private Context mContext;

    public MechantViewHold(Context context) {
        super(context);
        mContext = context;
    }

    public MechantViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void bind(MechantModel mechantModel){
        String mechantName = mechantModel.getName();
        mechantName = mechantName.substring(0, 1) + "**";
        ImageLoader.loadCircleImage(Tool.getPicUrl(mContext, mechantModel.getPhoto(), 44, 44), imgHeadView, R.drawable.dj_yh);
        txtName.setText(mechantName);
        txtGxmoney.setText("分润贡献:  ¥"+mechantModel.getProfit());

        txtStatu.setText("");

        if (!mechantModel.is_receipt()){
            txtStatu.setText("未收款");
            txtStatu.setTextColor(getResources().getColor(R.color.colorPrimary));
        }

        if (mechantModel.getAuth_status() == 0){
            txtStatu.setText("未实名认证");
            txtStatu.setTextColor(getResources().getColor(R.color.gray));
        }
    }

}
