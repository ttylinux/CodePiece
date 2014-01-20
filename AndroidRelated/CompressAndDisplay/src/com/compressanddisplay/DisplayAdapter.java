package com.compressanddisplay;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.*;

public class DisplayAdapter extends ArrayAdapter<String> {

	private ArrayList<String> list;
	private Context context;

	public DisplayAdapter(Context context, ArrayList<String> list) {
		super(context, 0, list);
		this.list = list;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup root) {
		Item item;

		String path = list.get(position);
		if (convertView == null) {
			item = new Item();
			convertView = View.inflate(context, R.layout.one_item, null);
			item.img = (ImageView) convertView.findViewById(R.id.one);

			convertView.setTag(item);
		} else {
			item = (Item) convertView.getTag();
		}

		MyApp.loader.displayImage(String.format("%s%s", "file://", path),
				item.img);

		return convertView;
	}

	private class Item {

		public ImageView img;
	}

}
