package com.tasawr.pixi.camera;

import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;

public class CameraPictureCallback implements PictureCallback{

	private IPictureCallback mPictureCallBack;
	
	public CameraPictureCallback() {
	}
	public void setPictureCallBackListener(IPictureCallback pictureCallback)
	{
		mPictureCallBack = pictureCallback;
		
	}
	@Override
	public void onPictureTaken(byte[] data, Camera camera) 
	{
		if(mPictureCallBack != null)
		{
			mPictureCallBack.pictureTakenData(data,camera);
		}
		
	}

}
