package com.sunil.picpoint;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    
	private ImageGroup imageGroup;
	private Context mContext;
	
	 
	public ImageAdapter(Context context, ImageGroup ig) {
	 imageGroup = ig;
	 mContext = context;
	}
	
	@Override
	public int getCount() {
		return imageGroup.getImageIds().length;
	}

	@Override
	public Object getItem(int position) {
		return imageGroup.getImageIds()[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ImageView imageView;
		if (convertView == null) {  // if it's not recycled, initialize some attributes
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(130, 130));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setAdjustViewBounds(true);
			imageView.setPadding(8, 8, 8, 8);
			
		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageResource(imageGroup.getImageIds()[position]);
		return imageView;
	}
	
	public void setImageGroup(ImageGroup ig){
		 imageGroup = ig;
		 Log.d("In Adapter", imageGroup.getImageNames().toString());
	}


}
