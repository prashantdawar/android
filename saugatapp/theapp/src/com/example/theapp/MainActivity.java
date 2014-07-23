package com.example.theapp;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private LovelyView myView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		myView = (LovelyView)findViewById(R.id.customView);
		
		/*myView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				myView.setCircleColor(Color.GREEN);
				myView.setLabelColor(Color.MAGENTA);
				myView.setLabelText("Help");
				
				Toast.makeText(getApplicationContext(), "View clicked", Toast.LENGTH_SHORT)
				.show();
			}
		});*/
	}

	 
	 
	public void btnPressed(View view){
		//myView.setCircleColor(Color.GREEN);
		//myView.setLabelColor(Color.MAGENTA);
		//myView.setLabelText("Help");
		
		
}
}