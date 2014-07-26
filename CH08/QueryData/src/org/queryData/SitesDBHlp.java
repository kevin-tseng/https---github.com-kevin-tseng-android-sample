package org.queryData;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SitesDBHlp extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "sites";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "sitesInfo";
	private static final String TABLE_CREATE = 
					"CREATE TABLE " + TABLE_NAME + " ( " +
					" id VARCHAR(10) NOT NULL, " +
					" name VARCHAR(30) NOT NULL, " +
					" phoneNo VARCHAR(20), " +
					" address VARCHAR(100), PRIMARY KEY (id)); ";
	
	public SitesDBHlp(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, 
			int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	//預先新增幾筆範例資料，以便之後查詢比對
	public void fillDB() {
		SQLiteDatabase db = getWritableDatabase();		
		ContentValues[] values = new ContentValues[3];
		for(int i=0; i<values.length; i++)
			values[i] = new ContentValues();
		
		values[0].put("id", "yangmingshan");
		values[0].put("name", "陽明山國家公園管理處");
		values[0].put("phoneNo", "02-28613601");
		values[0].put("address", "台北市北投區竹子湖路1之20號");
		
		values[1].put("id", "yushan");
		values[1].put("name", "玉山國家公園管理處");
		values[1].put("phoneNo", "049-2773121");
		values[1].put("address", "南投縣水里鄉中山路一段300號");
		
		values[2].put("id", "taroko");
		values[2].put("name", "太魯閣國家公園管理處");
		values[2].put("phoneNo", "03-8621100");
		values[2].put("address", "花蓮縣秀林鄉258號");	
		
		for(ContentValues row : values){
			db.insert(TABLE_NAME, null, row);
		}	
		db.close();
	}
	//取得指定旅遊地資訊
	public ArrayList<String> getAddress(String name){
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT name, address FROM " + TABLE_NAME + 
						" WHERE name LIKE ?";
		String[] args = {"%" + name + "%"};
		Cursor cursor = db.rawQuery(sql, args);
		ArrayList<String> addresses = new ArrayList<String>();
		int columnCount = cursor.getColumnCount();
		while(cursor.moveToNext()){
			String name_addr = "";
			for(int i=0; i<columnCount; i++)
				name_addr += cursor.getString(i) + "\n  ";
			addresses.add(name_addr);			
		}
		cursor.close();
		db.close();
		return addresses;		
	}
}
