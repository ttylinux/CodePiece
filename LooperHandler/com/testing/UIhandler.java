package com.testing;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;





public class UIhandler extends Handler
{

	private UIActivity activity;
	public UIhandler(UIActivity mActivity)
	{
		this.activity = mActivity;
	}

	@Override
	public void handleMessage(Message msg)
	{
		
	
		
		switch (msg.what)
		{
			case UIActivity.InfoToUI: //处理发送给UI的信息---可以访问UIActivity中的各个组件；
				                      //因为该Handler是在UI线程中创建的
				int result = (Integer) msg.obj;
				activity.result.setText("平方后的结果是："+result); 
				//Toast.makeText(activity, "平方后的结果是："+result, Toast.LENGTH_SHORT).show();
				//activity.showResult(result);
				break;
			default:
				break;
		}
	}
}
