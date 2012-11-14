package com.tasawr.pixi.activities;

import java.io.File;

import com.tasawr.pixi.R;



import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SelectImageActivity extends TopActivity implements OnClickListener{

	private View statusView;
	private Button btnBack,btnDone;
	private Button btnTakePictureFromCamera;
	private Button btnSelectImageFromGallery;
	private Button btnCancel;
	private static final int SELECT_PICTURE = 0;
	public static final String SELECTED_IMAGE_PATH = "IMAGE_PATH";


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestCustomTitle();
		setContentView(R.layout.activity_select_image);
		statusView = setCustomTitle(R.layout.custom_title);
		
		btnTakePictureFromCamera = (Button) findViewById(R.id.btnTakePhotoFromCamera);
		btnSelectImageFromGallery = (Button) findViewById(R.id.btnSelectImage);
		btnCancel = (Button) findViewById(R.id.btnCancel_select_image);

		btnTakePictureFromCamera.setOnClickListener(this);
		btnSelectImageFromGallery.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

	}
	@Override
	public void onClick(View v) {
		if(v == btnSelectImageFromGallery)
		{
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
		}
		else if(v == btnTakePictureFromCamera)
		{
			Intent intentCamraImage = new Intent(SelectImageActivity.this,
					CameraPictureSnapActivity.class);	
			startActivity(intentCamraImage);

		}
		else if(v == btnCancel) {

		}

	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && null != data)
		{
			Uri selectedImageUri = data.getData();
			String selectedImagePath = getPath(selectedImageUri); 
			String fileName = new File(selectedImagePath).getName();
			
			Intent intent = new Intent(SelectImageActivity.this, com.tasawr.pixi.activities.UploadImageActivity.class);
			intent.putExtra(SELECTED_IMAGE_PATH, selectedImagePath);
			startActivity(intent);

		}
	}
}
