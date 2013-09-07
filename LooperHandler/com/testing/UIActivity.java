package com.testing;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 *整个逻辑，是这样：
 *1.UI界面，发送一个信息，让WorkThread执行计算(通过WorkHandler的方式);
 *2.WorkThread计算完毕后，返回结果给UI线程(通过UIhandler)
 * 
 * 
 */
public class UIActivity extends Activity
{
	
	public static final int InfoToUI = 0x100;
	private UIhandler uihandler;
	private WorkHandler workHandler;
	private WorkThread workThread;
	public Button calBtn;
	public TextView result;
	public EditText edit;
	

	@Override
	protected void onCreate(Bundle state)
	{
		super.onCreate(state);
		setContentView(R.layout.main_ui);
		
		 initalMember();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		workThread = new WorkThread(this);
		workThread.start();
		//workHandler = workThread.getWorkHandler();会被告知，workHandler是null的
		
		/**
		 * 
		 *要在这里，对uiHandler进行初始化----否则，即使拥有uiHandler，也不可以操作UI线程中的各种界面资源 
		 */
		if(null == uihandler)
		{
			uihandler = new UIhandler(this);
		}
		
	}
	
	public UIhandler getUiHandler()
	{
		if(null == uihandler)
		{
			return null;
		}
		
		return uihandler;
	}
	
	private void initalMember()
	{
		calBtn = (Button)findViewById(R.id.button1);
		calBtn.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				workHandler = workThread.getWorkHandler();	 //要在这里
				if(null == workHandler)
				{
					Toast.makeText(UIActivity.this, "workhandler is null", Toast.LENGTH_SHORT).show();
					return;
				}
				
				Message msg = workHandler.obtainMessage();
				msg.what = WorkThread.InfoToWork;
				int content = Integer.parseInt(edit.getText().toString());
				msg.obj = content;
				
				workHandler.sendMessage(msg);
			}
		});
		
		result = (TextView)findViewById(R.id.result);
		edit = (EditText)findViewById(R.id.content);
		
	}
	
	
	/**
	 *被UIHandler调用的---UIHandler可以调用UIActivity 
	 */
	public void showResult(int mresult)
	{
		if(null == result)
		{
			return;
		}
		
		result.setText(""+mresult);
	}
	
}
