package com.example.imagesearch.activities;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

import com.example.imagesearch.R;
import com.example.imagesearch.adapters.ImageResultesAdapters;
import com.example.imagesearch.models.EndlessScrollListener;
import com.example.imagesearch.models.ImageResult;
import com.example.imagesearch.models.Settings;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	private EditText etSearch;
	private GridView gvResult;
	private ArrayList<ImageResult> imageResults;
	private ImageResultesAdapters aImageResults;
	private Settings settings;
	private String size;
	private String type;
	private String color;
	private String site;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		etSearch = (EditText) findViewById(R.id.etSearch);
		gvResult = (GridView) findViewById(R.id.gvResult);
		gvResult.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				Intent i = new Intent(SearchActivity.this, ImageViewActivity.class);
				ImageResult result = imageResults.get(position);
				i.putExtra("url", result.fullUrl);
				startActivity(i);
			}
			
		});
		
		gvResult.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
	                // Triggered only when new data needs to be appended to the list
	                // Add whatever code is needed to append new items to your AdapterView
		        loadMoreImage(page); 
		    }

			private void loadMoreImage(int page) {
				loadImage(page);
			}
	    });
		
		imageResults = new ArrayList<ImageResult>();
		aImageResults = new ImageResultesAdapters(this, imageResults);
		gvResult.setAdapter(aImageResults);
		settings = new Settings();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// Image search button
	public void onImageSearch(View v) {
		imageResults.clear();
		loadImage(0);
	}
	
	public String getUrl(int offset) {
		
		String query = etSearch.getText().toString();

		String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8";
		if ((size != null && !size.isEmpty())) {
			searchUrl += "&imgsz=" + size;
		}
		if ((type != null && !type.isEmpty())) {
			searchUrl += "&imgtype=" + type;
		}
		if ((color != null && !color.isEmpty())) {
			searchUrl += "&imgcolor=" + color;
		}
		if ((site != null && !site.isEmpty())) {
			searchUrl += "&as_sitesearch=" + site;
		}		
		
		if (offset > 0) {
			searchUrl += "&start=" + 8*offset;
		}
		return searchUrl;
	}
	
	public void loadImage(int offset) {
		AsyncHttpClient client = new AsyncHttpClient();
		String searchUrl = getUrl(offset);

		client.get(searchUrl, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				JSONArray imageResultsJson = null;
				try {
					imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
					aImageResults.addAll(ImageResult.fromArray(imageResultsJson));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void onSettingClick(MenuItem m) {
		Intent i = new Intent(this, SettingActivity.class);
    	// Pass args
    	i.putExtra("settings", settings);
    	// Execute Intent
    	startActivityForResult(i, 5);
	} 
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	if (requestCode == 5) {
    		if (resultCode == RESULT_OK) {
    			settings = (Settings) data.getSerializableExtra("settings");
    			size = settings.imageSize;
    			type = settings.imageType;
    			site = settings.siteFilter;
    			color = settings.colorFilter;
    		}
    	}
    }
}
