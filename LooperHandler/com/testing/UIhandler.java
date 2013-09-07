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
			case UIActivity.InfoToUI: //�����͸�UI����Ϣ---���Է���UIActivity�еĸ��������
				                      //��Ϊ��Handler����UI�߳��д�����
				int result = (Integer) msg.obj;
				activity.result.setText("ƽ����Ľ���ǣ�"+result); 
				//Toast.makeText(activity, "ƽ����Ľ���ǣ�"+result, Toast.LENGTH_SHORT).show();
				//activity.showResult(result);
				break;
			default:
				break;
		}
	}
}
