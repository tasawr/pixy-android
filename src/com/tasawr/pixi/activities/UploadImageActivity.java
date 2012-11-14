package com.tasawr.pixi.activities;

import java.io.File;

import com.tasawr.pixi.R;



import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class UploadImageActivity extends TopActivity implements OnClickListener{

	private ImageView imgViewUploadImg;
	private String selectedImagePath;
	private static final String TAG = "upload_Image";
	private View statusView;
	private Button btnUplaod,btnBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestCustomTitle();		
		setContentView(R.layout.activity_upload_image);
		
		statusView = setCustomTitle(R.layout.custom_title);
		imgViewUploadImg = (ImageView) findViewById(R.id.imgVwUpImage);
		
		//btnUplaod = (Button) statusView.findViewById(R.id.btnDone);
		//btnBack = (Button) statusView.findViewById(R.id.btnBack);
		
		selectedImagePath = getIntent().getStringExtra(SelectImageActivity.SELECTED_IMAGE_PATH);
		Log.i(TAG, selectedImagePath);
		
		if(null != selectedImagePath)			
			imgViewUploadImg.setImageURI(Uri.parse(new File(selectedImagePath).toString()));		
	}
	@Override
	public void onClick(View v) {
	
		
	}
	
}
