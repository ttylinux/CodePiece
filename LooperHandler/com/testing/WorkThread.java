package com.testing;

import android.os.Handler;
import android.os.Looper;

public class WorkThread extends Thread
{
	private WorkHandler handler;
	private UIActivity activity;
	public static final int QUIT= 0x12;
	public static final int InfoToWork = 0x11;
	
	public WorkThread(UIActivity mActivity){
		
		this.activity = mActivity;
		
	}

	
	
	
	/**
	 *��WorkThread��ΪLooper�߳� 
	 */
	@Override
	public void run(){
		
		Looper.prepare();
		handler = new WorkHandler(activity);	
		Looper.loop();
	}
	
	/**
	 *�������߳�ʹ�� 
	 */
	public WorkHandler  getWorkHandler()
	{
		return handler;
	}
}
