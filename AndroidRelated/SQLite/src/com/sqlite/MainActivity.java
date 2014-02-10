package com.sqlite;

import com.sqlite.OrderDataBase.OrderDataBaseOpenHelper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	private OrderDataBaseOpenHelper dataBaseHelper;
	private OrderDataBase dataBase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dataBase = new OrderDataBase(this);
		dataBaseHelper = dataBase.getDataBaseHelper();
		// dataBaseHelper.getWritableDatabase().insert(table, nullColumnHack,
		// values);
	   //String column = OrderDataBase.TableACompanion.TableAEntry.COLUMN_NAME_BUYER;
	}

}
