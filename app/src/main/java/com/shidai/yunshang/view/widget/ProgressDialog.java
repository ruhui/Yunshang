package com.shidai.yunshang.view.widget;

import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shidai.yunshang.R;
import com.shidai.yunshang.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EViewGroup(R.layout.view_progress_dialog)
public class ProgressDialog extends LinearLayout {

    @ViewById
    TextView tvMsg;


    public ProgressDialog(Context context) {
        super(context);
    }

    public ProgressDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTipMsg(String msg) {
        tvMsg.setText(TextUtils.isEmpty(msg) ? "加载中..." : msg);
    }

    public void setTipMsg(@StringRes int msgRes) {
        tvMsg.setText(msgRes);
    }




    public void removeFromView() {
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup != null) {
            viewGroup.removeView(this);
        }
    }

    public void show(){
        setVisibility(VISIBLE);
    }

    public void hide(){
        setVisibility(GONE);
    }


    public void addToView(ViewGroup viewParent) {
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup != null) {
            if (!Tool.equals(viewGroup, viewParent)) {
                viewGroup.removeView(this);
            }
        }
        if (viewGroup == null) {
            viewGroup = viewParent;
        }
        ViewGroup.LayoutParams params = null;
        if (viewParent instanceof LinearLayout) {
            RelativeLayout relate = new RelativeLayout(viewGroup.getContext());
            //LinearLayout复制父View
            LinearLayout newLinear = new LinearLayout(viewGroup.getContext());
            newLinear.setLayoutParams(viewGroup.getLayoutParams());
            //设置横竖
            newLinear.setOrientation(((LinearLayout) viewParent).getOrientation());
            ArrayList<View> viewHolder = new ArrayList<>();
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                viewHolder.add(viewGroup.getChildAt(i));
            }
            //清空所以的父级view
            viewGroup.removeAllViewsInLayout();
            for (View view : viewHolder) {
                newLinear.addView(view);
            }
            //把复制的view重新添加到新的view
            params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.CENTER_IN_PARENT);
            relate.addView(newLinear,params);
            //添加该view到新的view中
            relate.addView(this, params);
            //添加了加载view的布局添加到父view中
            viewGroup.addView(relate);
            return;
        } else if (viewParent instanceof RelativeLayout) {
            params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.CENTER_IN_PARENT);
        } else if (viewParent instanceof FrameLayout) {
            params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((FrameLayout.LayoutParams) params).gravity = Gravity.CENTER;
        }
        viewParent.addView(this, params);
    }


    public boolean isShowing() {
        return View.VISIBLE == this.getVisibility() && getParent() != null;
    }
}
