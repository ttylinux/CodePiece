package com.inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
/**
 *@author LuShuWei(albertxiaoyu@163.com) 
 */
public class MainActivity extends RoboActivity {

	@InjectView(R.id.title)
	TextView title;
	@InjectView(R.id.issue_edit)
	TextView tvIssue;
	@InjectView(R.id.detail_edit)
	EditText edTDetail;
	@InjectView(R.id.performer_addBtn)
	ImageButton performBtn;
	@InjectView(R.id.nav_btn_back)
	Button back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_create_checktask);
		inital();

	}

	private void inital() {
		title.setText("创建督察指令");
		tvIssue.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "弹出可选择的督察主题列表",
						Toast.LENGTH_SHORT).show();
			}
		});
		performBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "执行督察指令执行者的选择操作",
						Toast.LENGTH_SHORT).show();
			}
		});
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();	
			}
		});

	}

}
