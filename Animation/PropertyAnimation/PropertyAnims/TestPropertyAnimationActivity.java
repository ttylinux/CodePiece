package com.androidbook.propertyanimation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

public class TestPropertyAnimationActivity extends Activity 
{
	private static String tag = "My activity";
	private TextView m_tv = null;
	private MyAnimatableView m_atv = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        gatherControls();
    }
    private void gatherControls()
    {
    	m_tv = (TextView)this.findViewById(R.id.tv_id);
    	m_atv = new MyAnimatableView(m_tv);
    }
    public void toggleAnimation(View btnView)
    {
    	Button tButton = (Button)btnView;
    	//m_tv该对象必须要有相应的setter方法，在这里，要有setAlpha方法，且是public
    	if (m_tv.getAlpha() != 0)
    	{
	        ObjectAnimator fadeOut = 
	        	ObjectAnimator.ofFloat(m_tv, "alpha", 0f);
	        fadeOut.setDuration(5000);
	        fadeOut.start();
	        tButton.setText("Fade In");
    	}
    	else
    	{
	        ObjectAnimator fadeIn = 
	        	ObjectAnimator.ofFloat(m_tv, "alpha", 1f);
	        fadeIn.setDuration(5000);
	        fadeIn.start();
	        tButton.setText("Fade out");
    	}
    }
    public void sequentialAnimation(View bView)
    {
    	m_tv.setAlpha(1f);
        ObjectAnimator fadeOut = 
        	ObjectAnimator.ofFloat(m_tv, "alpha", 0f);
        ObjectAnimator fadeIn = 
        	ObjectAnimator.ofFloat(m_tv, "alpha", 1f);
        //通过AnimatorSet来组合多个动画对象ObjectAnimator
        AnimatorSet as = new AnimatorSet();
        as.playSequentially(fadeOut,fadeIn);
        as.setDuration(5000); //5 secs
        as.start();
    }
    public void sequentialAnimationXML(View bView)
    {
    	//动画效果可以再XML文件中描述
    	m_tv.setAlpha(1f);
    	AnimatorSet set = (AnimatorSet) 
	    	AnimatorInflater.loadAnimator(this,    
	    			R.animator.fadein);
    	set.setTarget(m_tv);
    	set.start();    
    }
    public void testAnimationBuilder(View v)
    {
    	m_tv.setAlpha(1f);
        ObjectAnimator fadeOut = 
        	ObjectAnimator.ofFloat(m_tv, "alpha", 0f);
        ObjectAnimator fadeIn = 
        	ObjectAnimator.ofFloat(m_tv, "alpha", 1f);
        AnimatorSet as = new AnimatorSet();
        as.play(fadeOut).before(fadeIn); //fadeIn效果先于fadeOut发生。
        as.setDuration(2000); //2 secs
        as.start();
    }
    public void testPropertiesHolder(View v)
    {
    	m_tv.setAlpha(1f);
    	float h = m_tv.getHeight();
    	float w = m_tv.getWidth();
    	float x = m_tv.getX();
    	float y = m_tv.getY();
    
    	//设置m_tv的当前位置
    	m_tv.setX(w);
    	m_tv.setY(h);
    	//Go to 50 on x
    	//这里设定的x是最终位置，而开始位置，由系统自己获取。
    	PropertyValuesHolder pvhX = 
    		PropertyValuesHolder.ofFloat("x", x);

    	//Go to 100 on y
    	PropertyValuesHolder pvhY = 
    		PropertyValuesHolder.ofFloat("y", y);
    	
    	ObjectAnimator oa
    	= ObjectAnimator.ofPropertyValuesHolder(m_tv, pvhX, pvhY);
        oa.setDuration(5000); //5 secs
        //开始和结束慢速，中间快速
        oa.setInterpolator(
        		new  AccelerateDecelerateInterpolator());
        oa.start();
    }
    public void testViewAnimator(View v)
    {
    	m_tv.setAlpha(1f);
    	float h = m_tv.getHeight();
    	float w = m_tv.getWidth();
    	float x = m_tv.getX();
    	float y = m_tv.getY();
    
    	m_tv.setX(w);
    	m_tv.setY(h);
    	
    	ViewGroup layout = (ViewGroup)m_tv.getParent();
    	layout.setClipChildren(true);
    	
    	//Go to 50 on x
    	ViewPropertyAnimator vpa = m_tv.animate();
    	vpa.x(x);
    	vpa.y(y);
    	
        vpa.setDuration(5000); //5 secs
        vpa.setInterpolator(
        		new  AccelerateDecelerateInterpolator());
        //vpa.start();
    }
    public void testTypeEvaluator(View v)
    {
    	m_tv.setAlpha(1f);
    	
    	float h = m_tv.getHeight();
    	float w = m_tv.getWidth();
    	float x = m_tv.getX();
    	float y = m_tv.getY();
    	
    	//m_atv要有公开的方法setpoint
        ObjectAnimator tea = 
        	ObjectAnimator.ofObject(m_atv
	        	,"point"
	        	,new MyPointEvaluator()
        		,new PointF(w,h)
	        	,new PointF(x,y));
        tea.setDuration(5000);
        tea.start();
    	
    }
    public void testKeyFrames(View v)
    {
    	m_tv.setAlpha(1f);
    	
    	float h = m_tv.getHeight();
    	float w = m_tv.getWidth();
    	float x = m_tv.getX();
    	float y = m_tv.getY();

    	//Start frame : 0.2--总时间的20%处
    	//alpha: 0.8
    	Keyframe kf0 = Keyframe.ofFloat(0.2f, 0.8f);
    	
    	//Middle frame: 0.5
    	//alpha: 0.2
    	Keyframe kf1 = Keyframe.ofFloat(.5f, 0.2f);
    	
    	//end frame: 0.8
    	//alpha: 0.8
    	Keyframe kf2 = Keyframe.ofFloat(0.8f, 0.8f);   
    	//设定了在整个动画时间中，会出现三个关键帧：
    	//处于20%的时间点，透明度为0.8;
    	//处于50%的时间点，透明度为0.2f；
    	//处于80%的时间点，透明度为0.8
    	PropertyValuesHolder pvhAlpha = 
    		PropertyValuesHolder.ofKeyframe("alpha", kf0, kf1, kf2);
    	
    	//从w移动到x--横坐标。
    	PropertyValuesHolder pvhX = 
    		PropertyValuesHolder.ofFloat("x", w, x);
    	
    	//end frame
        ObjectAnimator anim = 
        	ObjectAnimator.ofPropertyValuesHolder(m_tv, pvhAlpha,pvhX);
        anim.setDuration(5000);
        anim.start();
    }
}
