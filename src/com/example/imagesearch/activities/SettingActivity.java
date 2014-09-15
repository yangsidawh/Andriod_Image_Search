package com.example.imagesearch.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.imagesearch.R;
import com.example.imagesearch.models.Settings;

public class SettingActivity extends Activity {
	private Spinner spSize;
	private Spinner spColor;
	private Spinner spType;
	private EditText etSite;
	
	private String color;
	private String size;
	private String type;

	public Settings settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		settings  = (Settings) getIntent().getSerializableExtra("settings");

		spSize = (Spinner) findViewById(R.id.spSize);
		spColor = (Spinner) findViewById(R.id.spColor);
		spType = (Spinner) findViewById(R.id.spType);
		etSite = (EditText) findViewById(R.id.etSiteFilter);

		ArrayAdapter<CharSequence> spSizeAdapter = ArrayAdapter.createFromResource(this,
		        R.array.imageSizeList, R.layout.spinner_item);
		spSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spSize.setAdapter(spSizeAdapter);
		
		ArrayAdapter<CharSequence> spColorAdapter = ArrayAdapter.createFromResource(this,
		        R.array.imageColorList, R.layout.spinner_item);
		spColorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spColor.setAdapter(spColorAdapter);
		
		ArrayAdapter<CharSequence> spTypeAdapter = ArrayAdapter.createFromResource(this,
		        R.array.imageTypeList, R.layout.spinner_item);
		spTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spType.setAdapter(spTypeAdapter);
		
		spSize.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// TODO Auto-generated method stub
				size = parent.getItemAtPosition(pos).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
			
		});
		
		spType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// TODO Auto-generated method stub
				type = parent.getItemAtPosition(pos).toString();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
			
		});
		
		spColor.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// TODO Auto-generated method stub
				color = parent.getItemAtPosition(pos).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	public void onSaveAction(View v) {
		
		Intent i = new Intent();
		
		settings.colorFilter = color;
		settings.imageType = type;
		settings.imageSize = size;
		settings.siteFilter = etSite.getText().toString();
		
		i.putExtra("settings", settings);
		setResult(RESULT_OK, i);
		finish();
	}
}
