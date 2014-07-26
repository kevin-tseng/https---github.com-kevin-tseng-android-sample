package org.sharedPrefEx;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class SharedPrefEx extends Activity {
	private EditText etFileName;
	private EditText etSecAutoShoot;
	private Button btnSave;
	private Button btnLoad;
	private Button btnDefault;
	private RadioButton rbYes;
	private RadioButton rbNo;
	private String prefName = "prefSet";
	final private String default_fileName = "image";
	final private boolean default_isAutoFocus = true;
	final private int default_secAutoShoot = 10;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
        loadPref();
    }

	private void findViews() {
        etFileName = (EditText)findViewById(R.id.etFileName);
        etSecAutoShoot = (EditText)findViewById(R.id.etSecAutoShoot);
        rbYes = (RadioButton)findViewById(R.id.rbYes);
        rbNo = (RadioButton)findViewById(R.id.rbNo);
        btnSave = (Button)findViewById(R.id.btnSave);
        btnLoad = (Button)findViewById(R.id.btnLoad);
        btnDefault = (Button)findViewById(R.id.btnDefault);
        
        //按下「儲存偏好設定」按鈕
        btnSave.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				SharedPreferences settings = 
					getSharedPreferences(prefName, Context.MODE_PRIVATE);  
				
				String fileName = etFileName.getText().toString();
				
        		boolean isAutoFocus = true;
        		if (rbYes.isChecked())
        			isAutoFocus = true;
        		else
        			isAutoFocus = false;
        		        		
        		int secAutoShoot = 0;
        		try{
        			secAutoShoot = Integer.parseInt(
        				etSecAutoShoot.getText().toString());
        		}catch(NumberFormatException e){
        			Toast.makeText(SharedPrefEx.this, getText(R.string.askForNumber),
        				Toast.LENGTH_SHORT).show();
        		}        		
        		
        		settings.edit()
        		.putString("fileName", fileName)
        		.putBoolean("isAutoFocus", isAutoFocus)
        		.putInt("secAutoShoot", secAutoShoot)
        		.commit();
        		
    			Toast.makeText(SharedPrefEx.this, getText(R.string.prefSaved),
    				Toast.LENGTH_SHORT).show();
			}
		});
        
        //按下「載入偏好設定」按鈕
        btnLoad.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				loadPref();	
    			Toast.makeText(SharedPrefEx.this, getText(R.string.prefLoaded),
    				Toast.LENGTH_SHORT).show();
			}
		});
        
        //按下「回復預設值」按鈕
        btnDefault.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				loadDefault();
    			Toast.makeText(SharedPrefEx.this, getText(R.string.beenDefaultValue),
    				Toast.LENGTH_SHORT).show();
			}
		});
	}

	//載入偏好設定 
	protected void loadPref() {
		SharedPreferences settings = 
			getSharedPreferences(prefName, Context.MODE_PRIVATE); 		
		
		String fileName = settings.getString("fileName", default_fileName);
		etFileName.setText(fileName);
		
		boolean isAutoFocus = settings.getBoolean("isAutoFocus", default_isAutoFocus);
		if(isAutoFocus)
			rbYes.setChecked(true);
		else
			rbNo.setChecked(true);
		
		int secAutoShoot = settings.getInt("secAutoShoot", default_secAutoShoot);
		etSecAutoShoot.setText(Integer.toString(secAutoShoot));	
	}
	
	//載入預設值
	protected void loadDefault() {
		etFileName.setText(default_fileName);
		
		if(default_isAutoFocus)
			rbYes.setChecked(true);
		else
			rbNo.setChecked(true);
		
		etSecAutoShoot.setText(Integer.toString(default_secAutoShoot));		
	}
}