package com.tasawr.pixi.camera;

import android.hardware.Camera;

public interface IPictureCallback {	
	public void pictureTakenData(byte[] data, Camera camera);
}
