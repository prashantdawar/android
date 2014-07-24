package com.example.listactivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListViewActivity extends Activity {
	
	private static final String URL = "http://192.168.1.5/jsonsample/allproducts.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview_activity);
		
		StrictMode.ThreadPolicy policy = new StrictMode.
			    ThreadPolicy.Builder().permitAll().build();
			    StrictMode.setThreadPolicy(policy);
	    String feed = getFeed();
		final ArrayList<String> list = new ArrayList<String>();
		final ListView listview = (ListView) findViewById(R.id.listview);
		final StableArrayAdapter adapter = 
				new StableArrayAdapter(this,android.R.layout.simple_list_item_1,list);
		
		listview.setAdapter(adapter);
		
	    try {
	      JSONArray jsonArray = new JSONArray(feed);
	      Log.i(ListViewActivity.class.getName(),
	          "Number of entries " + jsonArray.length());

	      for (int i = 0; i < jsonArray.length(); i++) {
	        //JSONObject jsonObject = jsonArray.getJSONObject(i);
	        //list.add(jsonObject.getString("product"));
	    	  list.add(jsonArray.getString(i));
	    	  Log.i("jsonarraydata",jsonArray.getString(i));
		     // Log.i(ListViewActivity.class.getName(), "jsonArray firstelement"+ jsonObject.getString("v"));
	        //Log.i(ListViewActivity.class.getName(), jsonObject.getString("product"));
	        adapter.notifyDataSetChanged();
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }

		
		
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position,
					long id) {
			/*	final String item = (String) parent.getItemAtPosition(position);
				view.animate()
					.setDuration(2000)
					.alpha(0)
					.withEndAction(new Runnable() {
						
						@Override
						public void run() {
							list.remove(item);
							adapter.notifyDataSetChanged();
							view.setAlpha(1);
							
						}
					});
				*/
			}
			
		});
}
	
	
	private class StableArrayAdapter extends ArrayAdapter<String>{
		
		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context,int textViewResourceId, List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i=0; i< objects.size(); ++i){
				mIdMap.put(objects.get(i), i);
			}
		}
		
		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}
		
		@Override
		public boolean hasStableIds() {
			
			return true;
		}
	}
	public String getFeed(){
		StringBuilder builder = new StringBuilder();
	    HttpClient client = new DefaultHttpClient();
	    HttpGet httpGet = new HttpGet(URL);
	    try {
	      HttpResponse response = client.execute(httpGet);
	      StatusLine statusLine = response.getStatusLine();
	      int statusCode = statusLine.getStatusCode();
	      if (statusCode == 200) {
	        HttpEntity entity = response.getEntity();
	        InputStream content = entity.getContent();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
	        String line;
	        while ((line = reader.readLine()) != null) {
	          builder.append(line);
	        }
	      } else {
	        Log.e(ListViewActivity.class.toString(), "Failed to download file");
	      }
	    } catch (ClientProtocolException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return builder.toString();
	  }

}
