package com.tasawr.pixi.activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import com.tasawr.pixi.R;
import com.tasawr.pixi.camera.CameraView;
import com.tasawr.pixi.camera.IPictureCallback;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class CameraPictureSnapActivity extends TopActivity implements IPictureCallback,OnClickListener
{
	private Button btnClick,btnRetake,btnUse;
	private CameraView mCameraview;
	private byte[] tempData;
	private String TAG = "Camera";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);	
		disableScreenTurnOff();

		setContentView(R.layout.camera_view);

		btnClick = (Button) findViewById(R.id.buttonClick);
		btnRetake = (Button) findViewById(R.id.buttonRetake);
		btnUse = (Button) findViewById(R.id.buttonUse);
		
		mCameraview = (CameraView) findViewById(R.id.cameraView);		
		mCameraview.setPictureCallback(this);
		mCameraview.initPreview();

		btnClick.setOnClickListener(this);     
		btnRetake.setOnClickListener(this);   
		btnUse.setOnClickListener(this);   

		btnRetake.setVisibility(Button.GONE);
		btnUse.setVisibility(Button.GONE);
	}
	@Override
	protected void onPause() {		

		if(mCameraview != null)
		{
			mCameraview.stopPreview(true);			
		}
		super.onPause();
	}

	@Override
	protected void onResume() {	
		if(mCameraview != null)
		{
			mCameraview.resumePreview();
		}
		super.onResume();
	}

	@Override
	protected void onStop() {
		if(mCameraview != null)
		{
			mCameraview.stopPreview(true);	
		}
		super.onStop();
	}

	@Override
	protected void onDestroy() {		
		super.onDestroy();

		if(mCameraview != null)
		{
			mCameraview.stopPreview(false);
		}
	}


	/**
	 * Avoid that the screen get's turned off by the system.
	 */
	public void disableScreenTurnOff() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	/**
	 * Set's the orientation to landscape, as this is needed by AndAR.
	 */
	public void setOrientation()  {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	@Override
	public void pictureTakenData(byte[] data, Camera camera) {

		if(data != null)
		{
			tempData = data;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonClick:

			if(mCameraview != null)
			{
				mCameraview.takePicture();

				btnClick.setVisibility(Button.GONE);
				btnRetake.setVisibility(Button.VISIBLE);
				btnUse.setVisibility(Button.VISIBLE);

			}

			break;
		case R.id.buttonRetake:

			if(mCameraview != null)
			{
				mCameraview.resumePreview();

				btnClick.setVisibility(Button.VISIBLE);
				btnRetake.setVisibility(Button.GONE);
				btnUse.setVisibility(Button.GONE);
			}

			break;
		case R.id.buttonUse:

			String path = null;
			try 
			{
				path = saveToCard();
			} catch (IOException e) {
				Toast.makeText(CameraPictureSnapActivity.this, e.getMessage(),
						Toast.LENGTH_LONG).show();
				e.printStackTrace();
			} 

			if(path != null)
			{
				//Bundle bundle = new Bundle(); 	
				//bundle.putString("url", path);
				Intent intent = new Intent(CameraPictureSnapActivity.this,UploadImageActivity.class); 
				intent.putExtra(SelectImageActivity.SELECTED_IMAGE_PATH, path);
				startActivity(intent);
				finish();
				
			}

			break;

		default:
			break;
		}
	}

	private String saveToCard() throws IOException
	{
		FileOutputStream outStream = null;
		String imagePath = null;
		try {

			File dir = new File(Environment.getExternalStorageDirectory() + "/pixy");

			String state = Environment.getExternalStorageState();
			if(!state.equals(Environment.MEDIA_MOUNTED))  
			{
				throw new IOException("SD Card is not mounted.  It is " + state + ".");

			}

			if (!dir.exists()) 
			{				
				if(!dir.mkdir())
				{
					throw new IOException("Path to file could not be created.");				
				}
			}	 

			File outputFile = new File(dir, String.format("%d.jpg",System.currentTimeMillis()));
			imagePath  = outputFile.getAbsolutePath();
			// Write to SD Card
			outStream = new FileOutputStream(outputFile); // <9>
			outStream.write(tempData);
			outStream.close();
			Log.d(TAG, "onPictureTaken - wrote bytes: " + tempData.length);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}	
		return imagePath;
	}


}
