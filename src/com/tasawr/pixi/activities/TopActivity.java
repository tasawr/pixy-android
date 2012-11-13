package com.tasawr.pixi.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public abstract class TopActivity extends Activity
{
	private Context context;
	private Activity activity;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		context = getApplicationContext();
		activity = this;
	}
	
	@SuppressWarnings("rawtypes")
	protected void setCurrent(Class klass) {
		System.out.println("New activity: " + klass.getName());
		startActivity(new Intent(activity, klass));
	}
	
	protected void requestCustomTitle()
	{
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	}
	
	protected View setCustomTitle(int layoutId)
	{
        //set custom title 
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,layoutId); 
        View statusView = (View) getWindow().findViewById(layoutId);
        return statusView;
       
	}

}
