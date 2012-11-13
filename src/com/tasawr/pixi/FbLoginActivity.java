package com.tasawr.pixi;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class FbLoginActivity extends Activity{

    private static Facebook facebook = new Facebook("446499888718708");
    AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook);

    private SharedPreferences mPrefs;
    static TextView userName;
    Button fbLogin;
    Button fbLogout;
    static Context fb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       fb = this;
        /*
         * Get existing access_token if any
         */
        mPrefs = getPreferences(MODE_PRIVATE);
        String access_token = mPrefs.getString("access_token", null);
        long expires = mPrefs.getLong("access_expires", 0);
        if(access_token != null) {
            facebook.setAccessToken(access_token);
        }
        if(expires != 0) {
            facebook.setAccessExpires(expires);
        }
        
        /*
         * Only call authorize if the access_token has expired.
         */
        if(!facebook.isSessionValid()) {
        	 setContentView(R.layout.activity_fb_login);
        	 fbLogin = (Button) findViewById(R.id.fb_login_button);
        	 
        	 fbLogin.setOnClickListener(fbLoginHandaler);
        	 
             
        }
        else{
        	 setContentView(R.layout.maps);
        	 userName = (TextView) findViewById(R.id.user_name);
        	 fbLogout = (Button) findViewById(R.id.fb_logout);
        	 mAsyncRunner.request("me", new meRequestListener());
        	 
        	 fbLogout.setOnClickListener(fbLogOutHandaler);
        }
        
        
    }
    
    public static void myProfile(JSONObject me) throws JSONException{
    	//userName.setText(me.getString("name"));
    	Intent chatIntent = new Intent(fb,ChatActivity.class);
    	fb.startActivity(chatIntent);
    	Log.i("Me", me.toString());
    }
    
    View.OnClickListener fbLoginHandaler = new View.OnClickListener() {
        public void onClick(View v) {
          // it was the 1st button
        	
        	FbLoginActivity.facebook.authorize(FbLoginActivity.this, new String[] {}, new DialogListener() {
                @Override
                public void onComplete(Bundle values) {
                    SharedPreferences.Editor editor = mPrefs.edit();
                    editor.putString("access_token", facebook.getAccessToken());
                    editor.putLong("access_expires", facebook.getAccessExpires());
                    editor.commit();
                    finish();
	                startActivity(getIntent());
                }
    
                @Override
                public void onFacebookError(FacebookError error) {}
    
                @Override
                public void onError(DialogError e) {}
    
                @Override
                public void onCancel() {}
            });
        }
      };
      
      View.OnClickListener fbLogOutHandaler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mAsyncRunner.logout(FbLoginActivity.this, new RequestListener() {
				  @Override
				  public void onComplete(String response, Object state) {
					  SharedPreferences.Editor editor = mPrefs.edit();
	                  editor.putString("access_token", null);
	                  editor.putLong("access_expires", 0);
	                  editor.commit();
	                  finish();
	                  startActivity(getIntent());
				  }
				  
				  @Override
				  public void onIOException(IOException e, Object state) {}
				  
				  @Override
				  public void onFileNotFoundException(FileNotFoundException e,
				        Object state) {}
				  
				  @Override
				  public void onMalformedURLException(MalformedURLException e,
				        Object state) {}
				  
				  @Override
				  public void onFacebookError(FacebookError e, Object state) {}
				});
		}
	};
	public void onResume() {    
        super.onResume();
        facebook.extendAccessTokenIfNeeded(this, null);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebook.authorizeCallback(requestCode, resultCode, data);
    }
}