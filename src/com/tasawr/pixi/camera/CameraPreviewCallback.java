package com.tasawr.pixi.camera;

import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;

public class CameraPreviewCallback implements PreviewCallback{

	private IPreviewCallBack mPreviewCallback;
	public void setPreviewCallbackListener(IPreviewCallBack previewCallBack)
	{
		mPreviewCallback = previewCallBack;		
	}
	
	@Override
	public void onPreviewFrame(byte[] data, Camera camera) 
	{
		if(mPreviewCallback != null)
		{
			mPreviewCallback.onPreviewData(data, camera);
		}
		
	}

}
