package com.shidai.yunshang.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
    * recyclerview的间隔设置
    * @author ruhui
    * @time 2017/2/24 14:48
    * direction方向
   **/


public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

    public enum Direction {
        TOP, LEFT, RIGHT, BOTTOM
    }

    private int space,topspace,leftspace,rightspace,bottomspace;
    private Direction direction,directionTop,directionLeft,directionRight,directionBottom;


    public SpaceItemDecoration(int space, Direction direction) {
        this.space = space;
        this.direction = direction;
        init();
    }
    public SpaceItemDecoration(int topspace, Direction directionTop,
                               int bottomspace, Direction directionBottom,
                               int leftspace, Direction directionLeft,
                               int rightspace, Direction directionRight
                               ) {
        this.topspace = topspace;this.directionTop = directionTop;
        this.leftspace = leftspace;this.directionLeft = directionLeft;
        this.rightspace = rightspace; this.directionRight = directionRight;
        this.bottomspace = bottomspace;this.directionBottom = directionBottom;
    }

    private void init() {
        switch (direction){
            case TOP:
                topspace = space;
                directionTop = direction;
                break;
            case RIGHT:
                rightspace = space;
                directionRight = direction;
                break;
            case LEFT:
                leftspace = space;
                directionLeft = direction;
                break;
            case BOTTOM:
                bottomspace = space;
                directionBottom = direction;
                break;
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (directionTop != null){
            outRect.left = 0;
            outRect.top = topspace;
            outRect.bottom = 0;
            outRect.right = 0;
        }
        if (directionLeft != null){
            outRect.left = leftspace;
            outRect.top = 0;
            outRect.bottom = 0;
            outRect.right = 0;
        }
        if (directionRight!=null){
            outRect.left = 0;
            outRect.top = 0;
            outRect.bottom = 0;
            outRect.right = rightspace;
        }
        if (directionBottom !=null){
            outRect.left = 0;
            outRect.top = 0;
            outRect.bottom = bottomspace;
            outRect.right = 0;
        }

    }
}