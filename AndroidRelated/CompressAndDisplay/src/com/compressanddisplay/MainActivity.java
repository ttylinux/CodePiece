package com.compressanddisplay;

import java.io.File;
import java.io.FileNotFoundException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.compressanddisplay.tool.ExternalStorage;
import com.compressanddisplay.tool.GetDate;
import com.compressanddisplay.tool.TakePhoto;


import compress.CompressImage;
import compress.CompressImage.FileNotExistException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



public class MainActivity extends Activity {

	public static final String PhotoPaths = "PhotoPath";
	public static final String OnePath = "Path";
	public static final int RequestCode = 0x10;
	public static final int Success = 0x11;
	public static final int Failure = 0x12;

	private ExternalStorage storage = new ExternalStorage();
	private JSONArray array = new JSONArray();
	private String sourceFilePath;
	private String destFilePath;
	private int i;

	private MyApp myApp;
	private CompressImage compresser;
	private boolean ok = false;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Success:
				JSONObject one = new JSONObject();
				try {
					one.put(OnePath, destFilePath);
					array.put(one);
					ok = true;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case Failure:
				Toast.makeText(MainActivity.this, "Compress Failure",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myApp = (MyApp) getApplicationContext();
		compresser = CompressImage.createCompresser();
		
		inital();
	}

	private void inital() {
		Button take = (Button) findViewById(R.id.take);
		take.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sourceFilePath = storage.getExterDir().getAbsoluteFile() + "/"
						+ GetDate.getDate() + (i++);
				File file = new File(sourceFilePath);
				TakePhoto.takePicture(MainActivity.this, RequestCode, file);
			}
		});

		Button done = (Button) findViewById(R.id.done);
		done.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ok) {
					Intent intent = new Intent(MainActivity.this, Display.class);
					intent.putExtra(MainActivity.PhotoPaths, array.toString());
					startActivity(intent);
				} else {
					Toast.makeText(MainActivity.this, "还没有压缩完，不能跳转。",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && requestCode == RequestCode) {

			destFilePath = String.format("%s_%s", sourceFilePath, ".jpg");
			ok = false;
			startCompress();
		}
	}

	// 文件大小，超过240*400*4/1024 = 375KB，则进行压缩。
	private void startCompress() {
		new Thread() {

			@Override
			public void run() {
				try {

					compresser.compressImage(sourceFilePath, destFilePath, 375);
					handler.sendEmptyMessage(Success);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					handler.sendEmptyMessage(Failure);

				} catch (FileNotExistException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					handler.sendEmptyMessage(Failure);
				}
			}

		}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
