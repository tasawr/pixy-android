package com.tasawr.pixi.activities;



import com.tasawr.pixi.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectImageActivity extends TopActivity{

	private View statusView;
	private Button btnBack,btnDone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestCustomTitle();
		setContentView(R.layout.activity_main);
		statusView = setCustomTitle(R.layout.custom_title);
	}
}
