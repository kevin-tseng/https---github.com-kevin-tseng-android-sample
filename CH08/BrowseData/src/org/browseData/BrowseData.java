package org.browseData;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BrowseData extends Activity {
	private TextView tvRow; //資料筆數資訊
	private TextView tvId; //旅遊地代號
	private TextView tvName; //旅遊地名稱
	private TextView tvPhoneNo; //聯絡電話
	private TextView tvAddress; //地址
	private Button btnNext; //下一筆按鈕
	private Button btnBack; //上一筆按鈕
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

	/** 呈現指定旅遊地的資訊 */
	private void showSites(int index) {	
		if(sites.size()!=0){
			tvRow.setText((index + 1) + "/" + sites.size() + 
							getString(R.string.rowid_rowCount));
			tvId.setText(sites.get(index).getId());
			tvName.setText(sites.get(index).getName());
			tvPhoneNo.setText(sites.get(index).getPhoneNo());
			tvAddress.setText(sites.get(index).getAddress());	
		}else{
			tvRow.setText("0/0" + getString(R.string.noData));
			tvId.setText("");
			tvName.setText("");
			tvPhoneNo.setText("");
			tvAddress.setText("");				
		}
	}

	private void findViews() {
		tvRow = (TextView)findViewById(R.id.tvRow);
        tvId = (TextView)findViewById(R.id.tvId);
        tvName = (TextView)findViewById(R.id.tvName);
        tvPhoneNo = (TextView)findViewById(R.id.tvPhoneNo);
        tvAddress = (TextView)findViewById(R.id.tvAddress);     
        btnNext = (Button)findViewById(R.id.btnNext);
        btnBack = (Button)findViewById(R.id.btnBack);
        
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