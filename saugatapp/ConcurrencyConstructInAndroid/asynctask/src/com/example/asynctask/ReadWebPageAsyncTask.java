package com.example.asynctask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ReadWebPageAsyncTask extends Activity {

	private TextView textView;
	private Button dwWebPageBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		textView = (TextView) findViewById(R.id.TextView01);
		dwWebPageBtn = (Button) findViewById(R.id.readWebpage);
		
		dwWebPageBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				downloadWebPage();
			}
		});
	}
	private class DownloadWebPageTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... urls) {
			String response="";
			for (String url :urls){
				//creating http client 
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				
				//creating http get reuqest
				try {
					HttpResponse httpResponse = client.execute(httpGet);
					InputStream content = httpResponse.getEntity().getContent();
					
					BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
					String s = "";
					while((s = buffer.readLine()) != null){
						response += s;
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
							
			}
			return response;
		}
		
		@Override
		protected void onPostExecute(String result) {
			textView.setText(result);
		}
	}
	
	protected void downloadWebPage() {
		DownloadWebPageTask task = new DownloadWebPageTask();
		task.execute(new String[] { "http://www.vogella.com" } );
	}
	
	
	
}
