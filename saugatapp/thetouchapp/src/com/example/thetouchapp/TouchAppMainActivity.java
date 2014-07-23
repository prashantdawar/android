package com.example.thetouchapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class TouchAppMainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(new TouchAppView(this));
	}

	public class TouchAppView extends View{
		
		//Paint object
		private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		//variables for x and y point on screen
		private float x, y;
		//for checking occurence of touch event
		boolean touching = false;
		
		//image shown in main activity screen
		Bitmap drawingPic = 
				BitmapFactory.decodeResource(getResources(),R.drawable.drawing);
		
		//default position of image
		int drawingPic_x = 0;
		int drawingPic_y = 0;
		
		//capture height and width of image
		int drawingPic_w = drawingPic.getWidth();
		int drawingPic_h = drawingPic.getHeight();
		
		//The offset variables define the relative
		//position of an object in reference to some other object or position
		int drawingPic_offsetx;
		int drawingPic_offsety;
		
		//for checking touch event occurs and
		//this touch event occurs on image or any other place
		boolean dm_touched = false;
		
		public TouchAppView(Context context) {
			super(context);
		}
		
		protected void onDraw(Canvas canvas) {
			canvas.drawBitmap(drawingPic,drawingPic_x,drawingPic_y,paint);
		}
		
		protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec) {
			setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
								MeasureSpec.getSize(heightMeasureSpec));
		}
		
		public boolean onTouchEvent(MotionEvent event) {
			int action = event.getAction();
			switch(action){
			case MotionEvent.ACTION_DOWN:
				x = event.getX();
				y = event.getY();
				touching = true;
				
				if((x > drawingPic_x)
						&& (x < drawingPic_x+drawingPic_w)
						&& (y > drawingPic_y)
						&& (y < drawingPic_y+drawingPic_h)){
					drawingPic_offsetx = (int)x - drawingPic_x;
					drawingPic_offsety = (int)y - drawingPic_y;
					dm_touched = true;
				}
				break;
				
			case MotionEvent.ACTION_MOVE:
				x = event.getX();
				y = event.getY();
				touching = true;
				
				if(dm_touched){
					drawingPic_x = (int)x - drawingPic_offsetx;
					drawingPic_y = (int)y - drawingPic_offsety;
					}
					break;
					
			case MotionEvent.ACTION_UP:
			default:
			dm_touched = false;
			touching = false;
			
			}
			invalidate();
			return true;
		}
	}
}
