package com.example.imagesearch.adapters;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imagesearch.R;
import com.example.imagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

public class ImageResultesAdapters extends ArrayAdapter<ImageResult> {

	public ImageResultesAdapters(Context context, List<ImageResult> images) {
		super(context, R.layout.item_image_result, images);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageResult imageInfo = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
		}
		
		ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
		TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
		
		ivImage.setImageResource(0);
		tvTitle.setText(Html.fromHtml(imageInfo.title));
		Picasso.with(getContext()).load(imageInfo.tbUrl).into(ivImage);
		
		return convertView;
	}
	

}
