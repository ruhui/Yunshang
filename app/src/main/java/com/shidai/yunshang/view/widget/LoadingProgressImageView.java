package com.shidai.yunshang.view.widget;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class LoadingProgressImageView extends View {
	
	private Paint mPaint;
	
	int width,height;
	
	Context context;
	
	int progress=0;
	
	public LoadingProgressImageView(Context context)
	{
		this(context, null);
	}

	public LoadingProgressImageView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public LoadingProgressImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context=context;
		mPaint=new Paint();
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL); 
        
        mPaint.setColor(Color.parseColor("#70000000"));
        canvas.drawRect(0, 0, getWidth(), getHeight()-getHeight()*progress/100, mPaint);
        
        mPaint.setColor(Color.parseColor("#00000000"));
		//画阴影
        canvas.drawRect(0, getHeight()-getHeight()*progress/100, getWidth(),  getHeight(), mPaint);
        
        mPaint.setTextSize(30);
        mPaint.setColor(Color.parseColor("#FFFFFF"));
		mPaint.setStrokeWidth(2);
		Rect rect=new Rect();
		mPaint.getTextBounds("100%", 0, "100%".length(), rect);
		canvas.drawText(progress+"%", getWidth()/2-rect.width()/3,getHeight()/2, mPaint);
        
	}
	
	public void setProgress(int progress){
		this.progress=progress;
		postInvalidate();
	};
	
	
 }  

