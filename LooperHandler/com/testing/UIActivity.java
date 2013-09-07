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
 *�����߼�����������
 *1.UI���棬����һ����Ϣ����WorkThreadִ�м���(ͨ��WorkHandler�ķ�ʽ);
 *2.WorkThread������Ϻ󣬷��ؽ����UI�߳�(ͨ��UIhandler)
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
		//workHandler = workThread.getWorkHandler();�ᱻ��֪��workHandler��null��
		
		/**
		 * 
		 *Ҫ�������uiHandler���г�ʼ��----���򣬼�ʹӵ��uiHandler��Ҳ�����Բ���UI�߳��еĸ��ֽ�����Դ 
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
				workHandler = workThread.getWorkHandler();	 //Ҫ������
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
	 *��UIHandler���õ�---UIHandler���Ե���UIActivity 
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
