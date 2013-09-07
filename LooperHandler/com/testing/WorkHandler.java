package com.testing;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;





public class WorkHandler extends Handler
{

	private UIActivity activity;
	private UIhandler uiHandler;

	public WorkHandler(UIActivity mActivity)
	{

		this.activity = mActivity;

	}

	@Override
	public void handleMessage(Message msg)
	{

		if (null == uiHandler)
		{
			if (!getAndVaildeHandler())
			{
				return;
			}
		}

		switch (msg.what)
		{
			case WorkThread.InfoToWork:
				// 处理消息，然后将消息返回给UIHandler
				int content = (Integer) msg.obj;
				int result = content * content;
				Message toUi = uiHandler.obtainMessage();
				toUi.what = UIActivity.InfoToUI;
				toUi.obj = result;
				uiHandler.sendMessage(toUi);

				break;

			case WorkThread.QUIT: // 结束Looper,不抽取消息
				Looper.myLooper().quit();
				break;

			default:
				break;

		}
	}

	private boolean getAndVaildeHandler()
	{
		if (activity != null)
		{

			uiHandler = activity.getUiHandler();
			if (null == uiHandler)
			{
				Log.e("WorkHandler", "UIHandler is null");
				return false;
			}
			return true;
		}
		else
		{
			Log.e("WorkHandler", "UIActivity is null");
			return false;
		}
	}

}
