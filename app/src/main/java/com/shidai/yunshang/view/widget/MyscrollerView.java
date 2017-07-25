package com.shidai.yunshang.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2017/3/14.
 * 获取scrollerview的滚动高度
 */

@TargetApi(9)
public class MyscrollerView extends ScrollView {

    private ScrollerListeners sListeners;

    public void MyscrollerView(ScrollerListeners listeners){
        sListeners = listeners;
    }

    public MyscrollerView(Context context) {
        super(context);
    }

    public MyscrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        sListeners.scroller(scrollY);
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }


    public interface ScrollerListeners{
        public void scroller(int scrollY);
    };
}
