package com.example.httpurlconnectionexample;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainAcitivity extends Activity implements OnClickListener{

	private static final String USER_AGENT = "Mozilla/5.0";
	private TextView textView;
	private Button dwWebpageGetBtn;
	private Button dwWebpagePostBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		textView =(TextView) findViewById(R.id.TextView01);
		dwWebpageGetBtn = (Button) findViewById(R.id.loadWebpageViaGet);
		dwWebpagePostBtn = (Button) findViewById(R.id.loadWebpageViaPost);

		dwWebpageGetBtn.setOnClickListener(this);
		dwWebpagePostBtn.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.loadWebpageViaGet:
				downloadWebpageViaGet();
			break;
		case R.id.loadWebpageViaPost:
				downloadWebpageViaPost();
			break;
			
		}
	}

	private class DownloadWebPageTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... load) {
			StringBuffer response = new StringBuffer();
			try {					
					//Creating HttpURLConnection Request
					String url = load[0];
					String rMethod = load[1]; 
					URL obj = new URL(url);
					HttpURLConnection con = (HttpURLConnection) obj.openConnection();
					
					int responseCode=-100;
					
					//Setting Method for http request
					//Default is GET
					con.setRequestProperty("User-Agent", USER_AGENT);
					if(rMethod.equals("get")){
						con.setRequestMethod("GET");
						responseCode = con.getResponseCode();
					}
					
					//POST Request specific Parameters
					String urlParameters = "q=fifa";
					if(rMethod.equals("post")){
						con.setRequestMethod("POST");
						con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
						con.setDoOutput(true);
						DataOutputStream wr = new DataOutputStream(con.getOutputStream());
						wr.writeBytes(urlParameters);
						wr.flush();
						wr.close();
						responseCode = con.getResponseCode();
					}
									
					Log.i("Sending Request", rMethod + "to url:" + url + "response:" + responseCode);
									
					InputStreamReader ins = new InputStreamReader(con.getInputStream());
					BufferedReader in = new BufferedReader(ins);
					
					String inputLine;
					
					
					while((inputLine = in.readLine()) != null){
						response.append(inputLine);
					}
					in.close();
				} catch (MalformedURLException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			return response.toString();
		}
		@Override
		protected void onPostExecute(String result) {
			textView.setText(result);
		}
		
	}
	private void downloadWebpageViaPost() {
		DownloadWebPageTask task = new DownloadWebPageTask();
		task.execute(new String[] { "http://www.yahoo.com","post" });
	}

	private void downloadWebpageViaGet() {
		DownloadWebPageTask task = new DownloadWebPageTask();
		task.execute(new String[]{"http://www.google.com/search?q=brazil", "get"});
	}
}
