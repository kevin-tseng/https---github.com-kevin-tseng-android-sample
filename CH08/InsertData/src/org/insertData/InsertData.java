package org.insertData;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertData extends Activity {
	private EditText etId; //旅遊地代號
	private EditText etName; //旅遊地名稱
	private EditText etPhoneNo; //聯絡電話
	private EditText etAddress; //地址
	private Button btnInsert; //新增按鈕
	private Button btnClear; //清除按鈕
	private SitesDBHlp dbHlp; //SQLiteOpenHelper物件
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
    }

	@Override
	public void onResume() {
		super.onResume();
		if(dbHlp == null)
			dbHlp = new SitesDBHlp(this); 
	}

	@Override
	public void onPause() {
		super.onPause();
		if(dbHlp != null){
			dbHlp.close(); 
			dbHlp = null;
		}
	}
	
	private void findViews() {
        etId = (EditText)findViewById(R.id.etId);
        etName = (EditText)findViewById(R.id.etName);
        etPhoneNo = (EditText)findViewById(R.id.etPhoneNo);
        etAddress = (EditText)findViewById(R.id.etAddress);   
        btnInsert = (Button)findViewById(R.id.btnInsert);
        btnClear = (Button)findViewById(R.id.btnClear);
        
        //按下「新增」按鈕
        btnInsert.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				String id = etId.getText().toString().trim();
				String name = etName.getText().toString().trim();
				String phoneNo = etPhoneNo.getText().toString().trim();
				String address = etAddress.getText().toString().trim();
				if(id.equals("") || name.equals("")){
					Toast.makeText(InsertData.this, getString(R.string.blank), 
									Toast.LENGTH_SHORT).show();
					return;
				}
				
				StringBuilder sb = new StringBuilder();
				Site site = new Site(id, name, phoneNo, address); 
				long count = dbHlp.insertDB(site);
				if(count != -1){
					sb.append(getString(R.string.insert_success));
				}else{
					sb.append(getString(R.string.insert_fail));
				}
    			Toast.makeText(InsertData.this, sb, 
    							Toast.LENGTH_SHORT).show();
			}
		});
        
        //按下「清除」按鈕
        btnClear.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				etId.setText("");
				etName.setText("");
				etPhoneNo.setText("");
				etAddress.setText("");	
			}
		});
	}
}