package com.example.theapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

public class LovelyView extends View{
	
	//circle and text colors
	private int circleCol, labelCol;
	//label text
	private String circleText;
	//paint for drawing custom view
	private Paint circlePaint;
	
	private Animation anim;

	public LovelyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//paint object for drawing in onDraw
		circlePaint = new Paint();
		
		//get the attributes specified in attrs.xml using the name we included
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
		    R.styleable.LovelyView, 0, 0);
		
		try {
		    //get the text and colors specified using the names in attrs.xml
		    circleText = a.getString(R.styleable.LovelyView_circleLabel);
		    circleCol = a.getInteger(R.styleable.LovelyView_circleColor, 0);//0 is default
		    labelCol = a.getInteger(R.styleable.LovelyView_labelColor, 0);
		} finally {
		    a.recycle();
		}
	}
	
	private void createAnimation(Canvas canvas) {
        anim = new RotateAnimation(0, 360, getWidth()/2, getHeight()/2);
        anim.setRepeatMode(Animation.RESTART);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(10000L);
        startAnimation(anim);
   }
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		 
	    //draw the View
		//get half of the width and height as we are working with a circle
		int viewWidthHalf = this.getMeasuredWidth()/2;
		int viewHeightHalf = this.getMeasuredHeight()/2;
		//get the radius as half of the width or height, whichever is smaller
		//subtract ten so that it has some space around it
		int radius = 0;
		if(viewWidthHalf>viewHeightHalf)
		    radius=viewHeightHalf-10;
		else
		    radius=viewWidthHalf-10;
		
		circlePaint.setStyle(Style.FILL);
		circlePaint.setAntiAlias(true);
		//set the paint color using the circle color specified
		circlePaint.setColor(circleCol);
		canvas.drawCircle(viewWidthHalf, viewHeightHalf, radius, circlePaint);
		if (anim == null) {
            //createAnimation(canvas);
        }
        
		//set the text color using the color specified
		circlePaint.setColor(labelCol);
		//set text properties
		circlePaint.setTextAlign(Paint.Align.CENTER);
		circlePaint.setTextSize(50);
		
		//draw the text using the string attribute and chosen properties
		canvas.drawText(circleText, viewWidthHalf, viewHeightHalf, circlePaint);
		
		
		/*try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//Green Ring on pink circle
		final RectF rect = new RectF();
	    //Example values
	    rect.set(viewWidthHalf/2- radius, viewHeightHalf/2 - radius,
	    		viewWidthHalf/2 + radius, viewHeightHalf/2 + radius); 
	    circlePaint.setColor(Color.GREEN);
	    circlePaint.setStrokeWidth(20);
	   circlePaint.setAntiAlias(true);
	    circlePaint.setStrokeCap(Paint.Cap.ROUND);
	    circlePaint.setStyle(Paint.Style.STROKE);
	    canvas.drawArc(rect, -90, 360, false, circlePaint);
		
	}
	
	public int getCircleColor(){
	    return circleCol;
	} 
	 
	public int getLabelColor(){
	    return labelCol;
	}
	 
	public String getLabelText(){
	    return circleText;
	}
	
	public void setCircleColor(int newColor){
	    //update the instance variable
	    circleCol=newColor;
	    //redraw the view
	    invalidate();
	    requestLayout();
	}
	public void setLabelColor(int newColor){
	    //update the instance variable
	    labelCol=newColor;
	    //redraw the view
	    invalidate();
	    requestLayout();
	}
	public void setLabelText(String newLabel){
	    //update the instance variable
	    circleText=newLabel;
	    //redraw the view
	    invalidate();
	    requestLayout();
	}

}
