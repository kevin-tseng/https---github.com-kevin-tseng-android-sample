package org.updateData;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateData extends Activity {
	private TextView tvRow; //資料筆數資訊
	private TextView tvId; //旅遊地代號
	private EditText etName; //旅遊地名稱
	private EditText etPhoneNo; //聯絡電話
	private EditText etAddress; //地址
	private Button btnNext; //下一筆按鈕
	private Button btnBack; //上一筆按鈕
	private Button btnUpdate; //修改按鈕
	private Button btnDelete; //刪除按鈕
	private SitesDBHlp dbHlp; //SQLiteOpenHelper物件
	private ArrayList<Site> sites; //旅遊地資訊
	private int index; //依照index取得sites內資料
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initDB();
		findViews();
		showSites(index);
	}

	private void initDB() {
		if(dbHlp == null)
			dbHlp = new SitesDBHlp(this);
		dbHlp.fillDB();	
		sites = dbHlp.getAllSites();
	}

	//呈現指定旅遊地的資訊
	private void showSites(int index) {	
		if(sites.size()!=0){
			tvRow.setText((index + 1) + "/" + sites.size() + 
							getString(R.string.row));
			tvId.setText(sites.get(index).getId());
			etName.setText(sites.get(index).getName());
			etPhoneNo.setText(sites.get(index).getPhoneNo());
			etAddress.setText(sites.get(index).getAddress());	
		}else{
			tvRow.setText("0/0" + getString(R.string.noData));
			tvId.setText("");
			etName.setText("");
			etPhoneNo.setText("");
			etAddress.setText("");				
		}
	}

	private void findViews() {
		tvRow = (TextView)findViewById(R.id.tvRow);
		tvId = (TextView)findViewById(R.id.tvId);
		etName = (EditText)findViewById(R.id.etName);
		etPhoneNo = (EditText)findViewById(R.id.etPhoneNo);
		etAddress = (EditText)findViewById(R.id.etAddress);	 
		btnNext = (Button)findViewById(R.id.btnNext);
		btnBack = (Button)findViewById(R.id.btnBack);
		btnUpdate = (Button)findViewById(R.id.btnUpdate);
		btnDelete = (Button)findViewById(R.id.btnDelete);
		
		//按下「下一筆」按鈕
		btnNext.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				index++;
				if(index >= sites.size())
					index = 0;
				showSites(index);
			}
		});
		
		//按下「上一筆」按鈕
		btnBack.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				index--;
				if(index < 0)
					index = sites.size()-1;
				showSites(index);
			}
		});

		//按下「修改」按鈕
		btnUpdate.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				String id = tvId.getText().toString().trim();
				String name = etName.getText().toString().trim();
				String phoneNo = etPhoneNo.getText().toString().trim();
				String address = etAddress.getText().toString().trim();	
				if(id.equals("") || name.equals("")){
					Toast.makeText(UpdateData.this, getString(R.string.blank), 
									Toast.LENGTH_SHORT).show();
					return;
				}
				Site site = new Site(id, name, phoneNo, address); 
				int count = dbHlp.updateDB(site);
				Toast.makeText(UpdateData.this, 
						count + getString(R.string.updated), 
						Toast.LENGTH_SHORT).show();
				sites = dbHlp.getAllSites();
				index = 0;
				showSites(index);
			}
		});
		
		//按下「刪除」按鈕
		btnDelete.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				String id = tvId.getText().toString().trim(); 
				int count = dbHlp.deleteDB(id);
				Toast.makeText(UpdateData.this, 
						count + getString(R.string.deleted), 
						Toast.LENGTH_SHORT).show();
				sites = dbHlp.getAllSites();
				index = 0;
				showSites(index);
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(dbHlp == null)
			dbHlp = new SitesDBHlp(this); 
		sites = dbHlp.getAllSites();
		showSites(index);		
	}

	@Override
	public void onPause() {
		super.onPause();
		if(dbHlp != null){
			dbHlp.close(); //關閉資料庫連結
			dbHlp = null;
		}
		sites.clear(); //清空旅遊地資訊
	}
}