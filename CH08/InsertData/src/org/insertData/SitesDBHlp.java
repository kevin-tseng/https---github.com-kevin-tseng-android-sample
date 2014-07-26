package org.insertData;

import android.content.ContentValues;
import android.content.Context;
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
	private static final String COL_id = "id";
	private static final String COL_name = "name";
	private static final String COL_phoneNo = "phoneNo";
	private static final String COL_address = "address";
	
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
	//新增資料到指定的資料表內
	public long insertDB(Site site){
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COL_id, site.getId());
		values.put(COL_name, site.getName());
		values.put(COL_phoneNo, site.getPhoneNo());
		values.put(COL_address, site.getAddress());
		long count = db.insert(TABLE_NAME, null, values);
		db.close();
		return count;
	}
}
