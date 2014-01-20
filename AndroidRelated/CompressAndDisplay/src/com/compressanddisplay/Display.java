package com.compressanddisplay;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

public class Display extends Activity{
	
	
	private ListView listview;
	private DisplayAdapter adapter;
	
	@Override
	public void onCreate(Bundle state)
	{
		super.onCreate(state);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.display);
		inital();
	}
	
	private void inital()
	{
		listview = (ListView)findViewById(R.id.picList);
		adapter = new DisplayAdapter(this,getPath(getIntent()));
		listview.setAdapter(adapter);
		
	}
	
	private ArrayList<String> getPath(Intent intent)
	{
		
		String json = intent.getStringExtra(MainActivity.PhotoPaths);
		ArrayList<String> list = new ArrayList<String>();
		try {
			JSONArray array = new JSONArray(json);
			for(int i = 0 ; i < array.length(); i ++)
			{
				JSONObject one = (JSONObject) array.get(i);
				list.add(one.getString(MainActivity.OnePath));
				
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
