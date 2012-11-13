package com.tasawr.pixi.activities;



import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.tasawr.pixi.R;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MapMainActivity extends MapActivity implements OnClickListener,OnItemClickListener
{
	private Button btnCategory;
	private Button btnInfo;
	private ListView listViewCategory;
	private LinearLayout viewCategoryLayout;
	private ArrayAdapter<String> categoryAdapter;
	private MapView mapView;
	private Button btnSearchLocation;
	private Button btnAddLocation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_main);
		
		viewCategoryLayout = (LinearLayout) findViewById(R.id.ltCategories);
		btnCategory = (Button) findViewById(R.id.btnShowCategory);
		btnInfo = (Button) findViewById(R.id.btnShowInfo);
		listViewCategory = (ListView) findViewById(R.id.listVwCategory);
		btnAddLocation = (Button) findViewById(R.id.btnAddLocation);
		btnSearchLocation = (Button) findViewById(R.id.btnSearch);
		
		mapView = (MapView) findViewById(R.id.mapView_main);
		mapView.setBuiltInZoomControls(true);
		MapController mapController = mapView.getController();
		mapController.setZoom(8);
		mapView.setEnabled(true);
				
		btnCategory.setOnClickListener(this);
		btnInfo.setOnClickListener(this);
		btnAddLocation.setOnClickListener(this);
		btnSearchLocation.setOnClickListener(this);
		
		String[] categoryNames = getResources().getStringArray(R.array.search_category);
		categoryAdapter = new CategoryAdapter(this, R.layout.basic_text_list_row, categoryNames);
		listViewCategory.setAdapter(categoryAdapter);
		listViewCategory.setOnItemClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		if(v == btnCategory)
		{
			if(viewCategoryLayout.getVisibility() == View.GONE)
			{
				viewCategoryLayout.setVisibility(View.VISIBLE);
			}else if(viewCategoryLayout.getVisibility() == View.VISIBLE) {
				viewCategoryLayout.setVisibility(View.GONE);
			}		
			
		}
		else if(v == btnAddLocation)
		{
			Toast.makeText(getBaseContext(), "AddLocation", Toast.LENGTH_LONG).show();
		}
		else if(v == btnSearchLocation)
		{
			Toast.makeText(getBaseContext(), "Serach Loc", Toast.LENGTH_LONG).show();
		}else if (v == btnInfo) {
			startActivity(new Intent(MapMainActivity.this, com.tasawr.pixi.activities.SelectImageActivity.class));
		}
	}
	
	class CategoryAdapter extends ArrayAdapter<String>
	{
		private String [] categoryNames;
		private Context context;

		public CategoryAdapter(Context context, int textViewResourceId,
				String []  objects) {
			super(context, textViewResourceId, objects);
			categoryNames = objects;
			this.context = context;
		}
		
		@Override
		public int getCount() {
		
			return categoryNames.length;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			String categoryName = categoryNames[position];
			if(convertView == null)
			{
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.basic_text_list_row, null);
				TextView txtName = (TextView) convertView.findViewById(R.id.txtCategoryName);
				txtName.setText(categoryName);
				
			}
			return convertView;
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
		Toast.makeText(getBaseContext(), categoryAdapter.getItem(pos), Toast.LENGTH_LONG).show();
	}


}
