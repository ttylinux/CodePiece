package com.drawableanim;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;





public class MainActivity extends Activity
{
	
	private Animation transAnim;
	private AnimationDrawable framAnim;
	private ImageView img;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init()
	{
		transAnim = AnimationUtils.loadAnimation(this, R.anim.translate);
		img = (ImageView)findViewById(R.id.frame);
		img.setBackgroundResource(R.drawable.frames);
//		framAnim = (AnimationDrawable)img.getBackground(); -
		
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		framAnim = (AnimationDrawable)img.getBackground();
//		/**
//		 * 
//		 * 
//		 * It's important to note that the start() method called on the AnimationDrawable 
//		 * cannot be called during theonCreate() method of your Activity, 
//		 * because the AnimationDrawable is not yet fully attached to the window.
//		 *
//		 */
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if(MotionEvent.ACTION_DOWN == event.getAction())
		{
			img.startAnimation(transAnim);
			transAnim.setFillAfter(true);
			
			
			if(framAnim != null)
			{
				System.out.println("framAnim is not null");
				framAnim.start();
			}
			
		}
		return super.onTouchEvent(event);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

}
