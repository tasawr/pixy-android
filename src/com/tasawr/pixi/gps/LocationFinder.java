package com.tasawr.pixi.gps;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class LocationFinder {

	private Location location;
	
	public void startFind(Context context) 
	{
		LocationLooper looper = new LocationLooper();
		
		looper.start();
		
		while(!looper.isReady());
		
		looper.handler.post(new LocationBootstrapper(context));
		
	}
	public Location endFind()
	{
		int counter = 6;
		while(this.location == null)
		{
			try{Thread.sleep(5000);}catch(Exception e){}
			
			if(counter-- == 0)
			{
				break;
			}
		}		
		return this.location;
	}
	
	private class LocationLooper extends Thread
	{
		private Handler handler; 
		
		private LocationLooper()
		{
			
		}
		@Override
		public void run()
		{
			Looper.prepare();
			this.handler = new Handler();
			Looper.loop();
		}		
		private boolean isReady()
		{
			return this.handler != null;
		}
	}
	
	private class LocationBootstrapper implements Runnable
	{
		private Context context;		
		public LocationBootstrapper(Context context) 
		{
			this.context = context;
		}		
		@Override
		public void run()
		{
			LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
			
			LocationListener locationListener = new LocationListenerImpl();
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
			
			try{Thread.sleep(10000);}catch(Exception e){};
			locationManager.removeUpdates(locationListener);			
		}		
	}
	
	private class LocationListenerImpl implements LocationListener
	{
		@Override
		public void onLocationChanged(Location location) {		
			LocationFinder.this.location = location;
		}
		@Override
		public void onProviderDisabled(String provider) {		
			
		}
		@Override
		public void onProviderEnabled(String provider) {			
			
		}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {	
			
		}		
	}
	
}
