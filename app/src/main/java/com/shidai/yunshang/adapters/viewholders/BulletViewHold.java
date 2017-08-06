package com.shidai.yunshang.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.models.BulletinMode;
import com.shidai.yunshang.models.SystemModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/8/5.
 * 作者：黄如辉
 * 功能描述：
 */
@EViewGroup(R.layout.adapter_minemsg)
public class BulletViewHold extends LinearLayout {

    @ViewById(R.id.imgFirst)
    ImageView imgFirst;
    @ViewById(R.id.textView25)
    TextView txtTitle;
    @ViewById(R.id.textView26)
    TextView txtTime;
    @ViewById(R.id.textView27)
    TextView txtContent;

    public BulletViewHold(Context context) {
        super(context);
    }

    public BulletViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(BulletinMode model){
        if (model.is_read()){
            imgFirst.setVisibility(GONE);
        }else{
            imgFirst.setVisibility(VISIBLE);
        }
        txtTitle.setText(model.getTitle());
        txtTime.setText(model.getCreate_time());
        txtContent.setText(model.getContent());
    }
}
