package com.shidai.yunshang.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.models.GradesModel;
import com.shidai.yunshang.networks.ApiClient;
import com.shidai.yunshang.utils.ImageLoader;
import com.shidai.yunshang.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/7/29.
 * 作者：黄如辉
 * 功能描述：
 */
@EViewGroup(R.layout.adapter_benefit)
public class BenefitViewHold extends LinearLayout {

    @ViewById(R.id.textView42)
    TextView txtRight;
    @ViewById(R.id.imageView14)
    ImageView imgICon;
    @ViewById(R.id.txtName)
    TextView txtName;

    private Context mContext;

    public BenefitViewHold(Context context) {
        super(context);
        mContext = context;
    }

    public BenefitViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void bind(GradesModel gradesModel){
        String pictureUrl = ApiClient.BASE_URL_TEST + "content/images/grade/"+gradesModel.getGrade_id()+".png";
        ImageLoader.loadImage(pictureUrl, imgICon);
        txtRight.setText("共有"+gradesModel.getCount()+"位服务商");
        txtName.setText(gradesModel.getName());
    }
}
