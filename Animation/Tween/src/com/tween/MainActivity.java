package com.tween;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;





public class MainActivity extends Activity
{
	
	private ImageView one;
	private ImageView two;
	private ImageView three;
	
	private Animation rotateAnim;
	private Animation scaleAnim;
	private Animation translateAnim;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		inital();
		applyAnim();
	}

	
	private void inital()
	{
		one = (ImageView)findViewById(R.id.rotateType);
		two = (ImageView)findViewById(R.id.scaleType);
		three = (ImageView)findViewById(R.id.transportType);
	}
	
	private void applyAnim()
	{
		rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
		scaleAnim = AnimationUtils.loadAnimation(this, R.anim.scale);
		translateAnim = AnimationUtils.loadAnimation(this, R.anim.translate);
		
		one.startAnimation(rotateAnim);
		rotateAnim.setFillAfter(true);
		
		two.startAnimation(scaleAnim);
		scaleAnim.setFillAfter(true);
		
		three.startAnimation(translateAnim);
		translateAnim.setFillAfter(true);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
