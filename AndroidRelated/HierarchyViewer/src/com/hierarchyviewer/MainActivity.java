package com.hierarchyviewer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewStub;
import android.widget.*;

public class MainActivity extends SimpleViewServerActivity {

	private View msgView;
	private boolean flag = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		((Button) this.findViewById(R.id.rightButton))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						System.out.print("111");
						if (flag) {
							showMsgView();
						} else {
							closeMsgView();
						}
						flag = !flag;
					}
				});
	}

	private void showMsgView() {
		if (msgView != null) {
			msgView.setVisibility(View.VISIBLE);
			return;
		}
		ViewStub stub = (ViewStub) findViewById(R.id.msg_layout);
		msgView = stub.inflate();
	}

	private void closeMsgView() {
		if (msgView != null) {
			msgView.setVisibility(View.GONE);
		}
	}

}
