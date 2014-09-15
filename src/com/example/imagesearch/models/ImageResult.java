package com.example.imagesearch.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult {
	public String fullUrl;
	public String tbUrl;
	public String title;
	
	public ImageResult(JSONObject json) {
		try {
			this.fullUrl = json.getString("url");
			this.tbUrl = json.getString("tbUrl");
			this.title = json.getString("title");
		} catch (JSONException e) {
			// TODO: handle exception
		}
	}
	
	public static ArrayList<ImageResult> fromArray(JSONArray array) {
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		for (int i = 0; i< array.length(); i++) {
			try {
				results.add(new ImageResult(array.getJSONObject(i)));
			} catch (JSONException e) {
				// TODO: handle exception
			}
		}
		return results;
	}
}
