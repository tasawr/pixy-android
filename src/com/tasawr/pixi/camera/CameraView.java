package com.tasawr.pixi.camera;


import java.util.List;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback{

	private Camera camera;
	private int width;
	private int height;
	private Context mContext;
	private CameraPictureCallback mPictureCallback;
	private CameraPreviewCallback mPreviewCallBack;
	private IPictureCallback mIpicCallback;
	private IPreviewCallBack mIPrevCallback;
	private String TAG = "Camera";
	public CameraView(Context context) {
		
		super(context);
		mContext = context;
		SurfaceHolder previewHolder = this.getHolder();
		// TODO: check whether this is the right way to do it
		//		previewHolder.removeCallback(this); 
		previewHolder.addCallback(this);
		previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

	}
	public CameraView(Context context, AttributeSet attrs) {		
		super(context, attrs);

		mContext = context;
		SurfaceHolder previewHolder = this.getHolder();
		// TODO: check whether this is the right way to do it
		//		previewHolder.removeCallback(this); 
		previewHolder.addCallback(this);
		previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);		
		mPictureCallback = new CameraPictureCallback();
	}
	
	public void setPictureCallback(IPictureCallback pictureCallback)
	{
		mIpicCallback = pictureCallback;
		
		if(mPictureCallback != null)
		{
			mPictureCallback.setPictureCallBackListener(mIpicCallback);
		}
	}
	
	public void setPrevCallBack(IPreviewCallBack prevCallBack)
	{
		mIPrevCallback = prevCallBack;	
		
		if(mPreviewCallBack != null)
		{
			mPreviewCallBack.setPreviewCallbackListener(mIPrevCallback);
		}
	}
	
	
	public CameraView(Context context,
					IPictureCallback pictureCallback, 
					IPreviewCallBack previewCallback) {		
		super(context);
		
		mContext = context;
		SurfaceHolder previewHolder = this.getHolder();
		// TODO: check whether this is the right way to do it
		//		previewHolder.removeCallback(this); 
		previewHolder.addCallback(this);
		previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);	
		
		mPictureCallback = new CameraPictureCallback();
		mPictureCallback.setPictureCallBackListener(pictureCallback);
		
		mPreviewCallBack = new CameraPreviewCallback();
		mPreviewCallBack.setPreviewCallbackListener(previewCallback);		
	}
	@Override
	protected void finalize() throws Throwable {
		stopPreview(false);
		super.finalize();
	}
	// TODO: Implement the method
	public boolean initPreview(){

		if(camera == null)
			camera=Camera.open();			
		try {
			camera.setPreviewDisplay(this.getHolder());
		}
		catch (Throwable t) {
			Log.e("PreviewDemo-surfaceCallback",
					"Exception in setPreviewDisplay()", t);
			return false;
		}
		return true;
	}

	public void stopPreview(boolean temporaryStop){
		if(camera != null){
			camera.stopPreview();
			camera.release();
			camera=null;
		}		
		if(temporaryStop != true){
			SurfaceHolder previewHolder = this.getHolder();
			// TODO: Check what happens when callback is not added
			previewHolder.removeCallback(this);
		}
	}

	// TODO: Implement the method
	public void resumePreview(){
		// height width is not initialized yet
		if(height == 0 || width == 0)
			return;

		if(camera == null){
			if(initPreview() == false){
				return;
			}
		}

		Camera.Parameters parameters = camera.getParameters();
		int format = parameters.getPreviewFormat();

		List<Camera.Size> supportedSizes = parameters.getSupportedPreviewSizes();
		if(isPreviewSizeSupported(supportedSizes) == true){
			parameters.setPreviewSize(width,height);
		}
		else
			parameters.setPreviewSize(supportedSizes.get(0).width, supportedSizes.get(0).height);			

		parameters.setPictureFormat(PixelFormat.JPEG);
		parameters.setPictureSize(width, height);

		int w = parameters.getPreviewSize().width;
		int h = parameters.getPreviewSize().height;

		try {
			camera.setParameters(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		camera.startPreview();	
	}

	private boolean isPreviewSizeSupported(List<Size> prevSizes){
		for (Size size : prevSizes) {
			if(size.width == width && size.height == height)
				return true;
		}
		return false;
	}

	public void takePicture()
	{
		if(camera != null && mPictureCallback != null)
		{
			try {
				camera.takePicture(shutterCallback, 
						rawCallback,
						mPictureCallback);					
			} catch (Exception e) {
				e.printStackTrace();
			}
					
		}
	}

	ShutterCallback shutterCallback = new ShutterCallback() { // <6>
		public void onShutter() {
			Log.d(TAG, "onShutter'd");
		}
	};

	// Handles data for raw picture
	PictureCallback rawCallback = new PictureCallback() { // <7>
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.d(TAG, "onPictureTaken - raw");
		}
	};

	// Handles data for jpeg picture
	PictureCallback jpegCallback = new PictureCallback() { // <8>
		public void onPictureTaken(byte[] data, Camera camera) 
		{
			Log.d(TAG, "onPictureTaken - raw");			
		}
	};

	/************************* SurfaceHolder.Callback methods ****************************/	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		this.width = width;
		this.height = height;
		
		resumePreview();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		initPreview();
	}	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		stopPreview(true);		
	}
}
