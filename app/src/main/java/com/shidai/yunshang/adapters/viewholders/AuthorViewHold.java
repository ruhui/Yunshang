package com.shidai.yunshang.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.networks.ApiClient;
import com.shidai.yunshang.networks.responses.ShowupResponse;
import com.shidai.yunshang.utils.ImageLoader;
import com.shidai.yunshang.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/7/28.
 * 作者：黄如辉
 * 功能描述：
 */
@EViewGroup(R.layout.adapter_author)
public class AuthorViewHold extends LinearLayout {

    @ViewById(R.id.imageView14)
    ImageView imgIcon;
    @ViewById(R.id.txtName)
    TextView txtName;

    private Context mContext;

    public AuthorViewHold(Context context) {
        super(context);
        mContext=context;
    }

    public AuthorViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
    }

    public void bind(ShowupResponse model){
        String pictureUrl = ApiClient.BASE_URL_TEST + "content/images/grade/"+model.getId()+".png";
        ImageLoader.loadImage(pictureUrl, imgIcon);
        txtName.setText(model.getName());
    }
}
