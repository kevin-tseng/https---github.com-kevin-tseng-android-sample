package org.queryData;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class QueryData extends Activity {
	private EditText etPlaceName; //輸入地名
	private Button btnSubmit; //查詢按鈕
	private SitesDBHlp dbHlp;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		connectDB(); 
		findViews(); 
    }	

	private void connectDB() {
		if(dbHlp == null)
			dbHlp = new SitesDBHlp(this);
		dbHlp.fillDB(); 
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
		etPlaceName = (EditText)findViewById(R.id.etPlaceName);
		btnSubmit = (Button)findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				StringBuilder address = new StringBuilder("");
				String placeName = etPlaceName.getText().toString().trim();
				if(!placeName.equals("")){
					ArrayList<String> addresses = dbHlp.getAddress(placeName);
					if(addresses.size()>0){
						for(String addr: addresses)
							address.append(addr + "\n");
					}else{
						address.append("找不到指定地點");						
					}
				} else{
					address.append("請輸入旅遊地名稱");
				}
				Toast.makeText(QueryData.this, 
						address, Toast.LENGTH_LONG).show();				
			}
		});
	}
}