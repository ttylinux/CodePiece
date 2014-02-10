package com.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class OrderDataBase {

	private static final String DATABASE_NAME = "OrderDataBase.db";
	private static int DATABASE_VERSION = 1;

	private final OrderDataBaseOpenHelper dataBaseHelper;

	public OrderDataBase(Context context) {
		dataBaseHelper = new OrderDataBaseOpenHelper(context);
	}

	public OrderDataBaseOpenHelper getDataBaseHelper() {

		return dataBaseHelper;
	}

	/**
	 * A helper class to manage database creation and version management.
	 */
	public class OrderDataBaseOpenHelper extends SQLiteOpenHelper {

		private static final String TAG = "OrderDataBaseOpenHelper";
		private SQLiteDatabase orderDataBase; // reference to a dataBase

		public OrderDataBaseOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			// perform create table in a dataBase
			Log.d(TAG, "trigger onCreate, inital dataBase Contents");
			orderDataBase = db;
			createTableA(orderDataBase);
			createTableB(orderDataBase);
			createTableC(orderDataBase);
		}

		private void createTableA(SQLiteDatabase db) {
			// TableACompanion
		}

		private void createTableB(SQLiteDatabase db) {
			// TableBCompanion
		}

		private void createTableC(SQLiteDatabase db) {
			// TableCCompanion

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

			Log.w(TAG, "update dataBase, oldVersion:" + oldVersion
					+ ";newVersion:" + newVersion);
			// perform upgrade action, such as delete table,change table, add
			// table

		}

	}

	// companion class for TableA, TableB, TableC
	public static class TableACompanion implements BaseColumns {

		private TableACompanion() {

		}

		public static abstract class TableAEntry implements BaseColumns {

			public static final String TABLE_NAME = "TableA";
			public static final String COLUMN_NAME_ENTRY_ID = "Aid";
			public static final String COLUMN_NAME_ORDER = "order";
			public static final String COLUMN_NAME_BUYER = "buyer";

		}

	}

	public static class TableBCompanion {

		private TableBCompanion() {

		}

		public static abstract class TableBEntry implements BaseColumns {

			public static final String TABLE_NAME = "TableB";
			public static final String COLUMN_NAME_ENTRY_ID = "Bid";
			public static final String COLUMN_NAME_ORDER = "order";
			public static final String COLUMN_NAME_BUYER = "buyer";

		}

	}

	public static class TableCCompanion {

		private TableCCompanion() {

		}

		public static abstract class TableCEntry implements BaseColumns {

			public static final String TABLE_NAME = "TableC";
			public static final String COLUMN_NAME_ENTRY_ID = "Cid";
			public static final String COLUMN_NAME_ORDER = "order";
			public static final String COLUMN_NAME_BUYER = "buyer";

		}

	}

}
