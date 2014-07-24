package com.example.gms;

import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class RegisterApp extends AsyncTask<Void, Void, String> {

	private static final String TAG = "GCMRelated";
	Context ctx;
	GoogleCloudMessaging gcm;
	String SENDER_ID = "1041455921393";
	String regid = null; 
	private int appVersion;

	
	public RegisterApp(Context ctx, GoogleCloudMessaging gcm, int appVersion){
		this.ctx = ctx;
		this.gcm = gcm;
		this.appVersion = appVersion;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
	
	@Override
	protected String doInBackground(Void... params) {
		String msg = "";
		try{
			if (gcm == null){
				gcm = GoogleCloudMessaging.getInstance(ctx);
			}
			regid = gcm.register(SENDER_ID);
            msg = "Device registered, registration ID=" + regid;
            Log.i(TAG,msg);
            storeRegistrationId(ctx, regid);
		} catch (IOException e){
			msg = "Error: " + e.getMessage();
		}
		
		return msg;
	}

	private void storeRegistrationId(Context ctx, String regid) {
		final SharedPreferences prefs = ctx.getSharedPreferences(MainActivity.class.getSimpleName(),
	             Context.MODE_PRIVATE);
	     Log.i(TAG, "Saving regId on app version " + appVersion);
	     SharedPreferences.Editor editor = prefs.edit();
	     editor.putString("registration_id", regid);
	     editor.putInt("appVersion", appVersion);
	     editor.commit();
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		Toast.makeText(ctx, "Registration Completed. Now you can see the notifications", Toast.LENGTH_SHORT).show();
		  Log.v(TAG, result); 
	}
}
