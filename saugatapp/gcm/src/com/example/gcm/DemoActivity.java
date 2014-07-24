package com.example.gcm;


import java.io.IOException;
import java.util.Random;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DemoActivity extends Activity {

	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";

	private static final int MAX_ATTEMPTS = 5;
	private static final int BACKOFF_MILLI_SECONDS = 2000;
	private static final Random random = new Random();

	String SENDER_ID = "284885765257";

	static final String TAG = "GCMDemo";

	TextView mDisplay;
	Context context;
	GoogleCloudMessaging gcm;

	String regid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		mDisplay = (TextView) findViewById(R.id.display);

		context = getApplicationContext();

		gcm = GoogleCloudMessaging.getInstance(this);
		regid = getRegistrationId(context);

		if(regid.isEmpty()){
			registerInBackground();
		}

	}

	private void registerInBackground() {

		new AsyncTask<Void, Void, String>(){

			@Override
			protected String doInBackground(Void... params) {
				String msg="";
				long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);

				for (int i = 1; i <= MAX_ATTEMPTS; i++) {
					Log.d(TAG, "Attempt #" + i + " to register");
					try{	
						if (gcm == null){
							gcm = GoogleCloudMessaging.getInstance(context);
						}

						regid = gcm.register(SENDER_ID);
						msg = "Device registered, registration ID=" + regid;
						Log.i(TAG,msg);
						sendRegistrationIdToBackEnd();

						storeRegistrationId(context, regid);
						
					} catch(IOException e){
						msg = "Error: " + e.getMessage();
						Log.e(TAG, "Failed to register on attempt " + i + ":" + e);
						if (i == MAX_ATTEMPTS) {
							break;
						}
						try {
							Log.d(TAG, "Sleeping for " + backoff + " ms before retry");
							Thread.sleep(backoff);
						} catch (InterruptedException e1) {
							// Activity finished before we complete - exit.
							Log.d(TAG, "Thread interrupted: abort remaining retries!");
							Thread.currentThread().interrupt();
							return "";
						}
						// increase backoff exponentially
						backoff *= 2;
					}

				}
				return msg;
			}
			@Override
			protected void onPostExecute(String msg) {
				mDisplay.append(msg + "\n");
			}
		}.execute(null,null,null);

	}

	protected void sendRegistrationIdToBackEnd() {
		// TODO Auto-generated method stub

	}

	protected void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);
		Log.i(TAG, "Saving regid on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.putString(PROPERTY_REG_ID, regId);
		editor.commit();


	}

	// Send an upstream message.
	public void onClick(final View view) {

		//Empty
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if(registrationId.isEmpty()){
			Log.i(TAG, "Registration not found.");
			return "";
		}
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i(TAG,"App version changed.");
			return "";
		}
		return registrationId;
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			throw new RuntimeException("Could not get package name:" + e);
		}

	}

	private SharedPreferences getGCMPreferences(Context context2) {
		return getSharedPreferences(DemoActivity.class.getSimpleName(), Context.MODE_PRIVATE);
	}

}
