package com.androidinterview;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;


/**
 *@author lushuwei(Albert) 
 */


public class MainActivity extends Activity
{

	private Button prsbtn;
	private EditText edit;
	private Spinner spinner1;
	private Spinner spinner2;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		inital();

		prsbtn.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				String str = edit.getEditableText().toString();
				Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void inital()
	{
		prsbtn = (Button) findViewById(R.id.button1);
		edit = (EditText) findViewById(R.id.editText1);

		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);

		String[] str = { "广东", "香港" };
		String[] str1 = { "广州", "汕头" };
		String[] str2 = { "新界", "九龙" };

		// ArrayAdapter<String> orginArray = new ArrayAdapter<String>(this,0,str);
		// orginArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// spinner1.setAdapter(orginArray);
		// 要用自定义的ArrayAdapter才可以---上述的不可以。----不可行

		OneArrayAdapter oneArray = new OneArrayAdapter(this, str);
		oneArray.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spinner1.setAdapter(oneArray);
		//---可行

//		在使用BaseAdapter的时候，不需要设定DropDownViewResource
//		 OneBaseAdapter one = new OneBaseAdapter(str,this);
//		 spinner1.setAdapter(one);
		 

		final OneBaseAdapter two = new OneBaseAdapter(str1, this);
		final OneBaseAdapter three = new OneBaseAdapter(str2, this);

		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				// TODO Auto-generated method stub

				if (0 == arg2)
				{
					spinner2.setAdapter(two);
				}

				if (1 == arg2)
				{
					spinner2.setAdapter(three);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
				// TODO Auto-generated method stub

			}
		});

		// spinner1.setOnItemClickListener(new AdapterView.OnItemClickListener()
		// spinner不支持OnItemClickeListener

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
