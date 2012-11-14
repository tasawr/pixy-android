package com.tasawr.pixi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class WebService {

	
private static String getDataFromWeb(String urlLink , Integer b, JSONObject jsonObj ) {
	
		StringBuilder builder = new StringBuilder();
		DefaultHttpClient httpclient = new DefaultHttpClient();
		
		HttpHost targetHost = new HttpHost("50.57.135.55", 3000, "http");
		 BasicHttpContext localcontext = new BasicHttpContext();

		try {
			//HttpResponse response = httpclient.execute(request);
			HttpResponse response;
			if(b == 1){
				HttpPost request = new HttpPost(urlLink);
				
				//
				StringEntity entity = new StringEntity(jsonObj.toString(), HTTP.UTF_8);
				entity.setContentType("application/json");
				request.setEntity(entity);
				
			    response = httpclient.execute(targetHost, request, localcontext);
			}
			else if(b == 2){
				HttpPut request = new HttpPut(urlLink);
				response = httpclient.execute(targetHost, request, localcontext);
			}
			else if(b == 3){
				HttpDelete request = new HttpDelete(urlLink);
				response = httpclient.execute(targetHost, request, localcontext);
			}
			else{
				HttpGet request = new HttpGet(urlLink);
				response = httpclient.execute(targetHost, request, localcontext);
			}
			
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				
				Log.e("Error", "Failed to download file"+statusCode);
				
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return builder.toString();

	}

 public  void place() throws JSONException {
	String url = "http://50.57.135.55:3000/place";
	
	// Build the JSON object to pass parameters
	JSONObject jsonObj = new JSONObject();
	jsonObj.put("name", "Mohakhali");
	jsonObj.put("description", "Tasawr office");
	 
	JSONArray js = new JSONArray();
	js.put(24);
    js.put(90);
    jsonObj.putOpt("geo", js);
	Log.i("String", jsonObj.toString());
	//String data = getDataFromWeb(url,1, jsonObj);
	
	//Log.i("Data ", data);
	JSONObject jo = new JSONObject();
	 String data = getDataFromWeb(url,4,jo);
	Log.i("Data ", data);
//	try {
//		JSONArray ja = new JSONArray(data);
//		for (int i = 0; i < ja.length(); i++) {
//			JSONObject jo = (JSONObject) ja.get(i);
//			Log.i("JSON Data", jo.getString("name"));
//			Log.i("JSON Data", jo.getString("description"));
//			Log.i("JSON Data", jo.getString("_id"));
//		}
//	} catch (JSONException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	
}
}
