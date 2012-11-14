package com.tasawr.pixi.camera;

import android.hardware.Camera;

public interface IPreviewCallBack {
	
	public void onPreviewData(byte[] data, Camera camera);

}
