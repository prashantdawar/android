package com.example.handler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressTestActivity extends Activity {

	private ProgressBar progress;
	private TextView text;
	private Button progressButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		progress = (ProgressBar) findViewById(R.id.progressBar1);
		text = (TextView) findViewById(R.id.textView1);
		//start progress on button press
		showProgress();

	}

	private void showProgress() {

		progressButton = (Button) findViewById(R.id.button1);	
		progressButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//progress through thread
				startProgress(v);

			}
		});
	}

	protected void startProgress(View v) {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				for (int i=0; i<=10; i++){
					final int value=i;
					//simulation of long running task
					doFakeWork();
					//posting data to UI thread
					progress.post(new Runnable() {
						@Override
						public void run() {
							text.setText("updating");
							progress.setProgress(value);

						}
					});
				}
			}
		};
		
		//thread starts
		new Thread(runnable).start();
	}

	protected void doFakeWork() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
