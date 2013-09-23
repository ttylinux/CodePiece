package com.androidinterview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 *@author lushuwei(Albert) 
 */
public class OneArrayAdapter extends ArrayAdapter<String>
{
	
	private Context context;
	private String[] str;

	public OneArrayAdapter(Context mContext, String[] mStr)
	{
		super(mContext, 0, 0, mStr);
		
		this.context = mContext;
		this.str = mStr;
	}
	

	@Override
	public int getCount()
	{
		return 2;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup viewgroup)
	{
		TextView text;
		
//		 text = new TextView(context);
//		text.setText(str[position]);
//    ----在BaseAdapter中也是可行的；可行
		
		
		//下述代码，在BaseAdapter中是不可行的；但是在这里可行
		 if(convertView == null)
		 {
		 convertView = View.inflate(context, R.layout.text, null);
		 text = (TextView)convertView.findViewById(R.id.text);
		 text.setText(str[position]);
		 convertView.setTag(text);
		
		 }else
		 {
		 text = (TextView)convertView.getTag();
		 if(text == null)
		 {
		 text = new TextView(context);
		 text.setText(str[position]);
		 }
		 }
		
		return text;
	}

}
