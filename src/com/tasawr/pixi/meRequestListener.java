package com.tasawr.pixi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.FacebookError;

public class meRequestListener implements RequestListener {

	@Override
	public void onComplete(String response, Object state) {
		// TODO Auto-generated method stub
		 //Log.i("ME", response);
		 try {
			
			JSONObject jo = new JSONObject(response);
			FbLoginActivity.myProfile(jo);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			
			Log.i("Eroorr", "erorr");
			e.printStackTrace();
		}
		 
	}

	@Override
	public void onIOException(IOException e, Object state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFileNotFoundException(FileNotFoundException e, Object state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMalformedURLException(MalformedURLException e, Object state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFacebookError(FacebookError e, Object state) {
		// TODO Auto-generated method stub

	}

}
