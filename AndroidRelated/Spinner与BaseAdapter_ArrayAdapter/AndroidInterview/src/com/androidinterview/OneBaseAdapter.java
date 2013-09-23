package com.androidinterview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;





public class OneBaseAdapter extends BaseAdapter
{
	private String[] str;
	private Context context;

	public OneBaseAdapter(String[] mStr, Context mContext)
	{

		this.str = mStr;
		this.context = mContext;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub

		TextView text;
		text = new TextView(context);
		text.setText(str[position]);
		// ---可行

		// 下述代码不可行。在BaseAdapter中不可以直接inflate;但是在ArrayAdapter中可行
		// if(convertView == null)
		// {
		// convertView = View.inflate(context, R.layout.text, null);
		// text = (TextView)convertView.findViewById(R.id.text);
		// text.setText(str[position]);
		// convertView.setTag(text);
		//
		// }else
		// {
		// text = (TextView)convertView.getTag();
		// if(text == null)
		// {
		// text = new TextView(context);
		// text.setText(str[position]);
		// }
		// }

		return text;
	}

}
