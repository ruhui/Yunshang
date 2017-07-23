package com.shidai.yunshang.view.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shidai.yunshang.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;


@EViewGroup(R.layout.view_nav_bar_empty)
public class NavBarEmpty extends Toolbar {
    public NavBarEmpty(Context context) {
        super(context);
    }

    public NavBarEmpty(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
