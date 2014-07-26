package org.telephonyEx;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
public class Result extends Activity {
	TextView tvInfo; //用來顯示相關資訊
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        findViews();
        showResults();
    }
	private void findViews(){
		tvInfo = (TextView)findViewById(R.id.tvInfo);
    }
	private void showResults(){
		Bundle bundle = this.getIntent().getExtras();
		int requestCode = bundle.getInt("requestCode");
		StringBuilder sb = new StringBuilder();
		switch(requestCode){
			case 0: //代表有來訊
				sb.append(getString(R.string.sendAddr) + 
						bundle.getString("sendAddr") + "\n");
				sb.append(getString(R.string.time) + 
						bundle.getString("time") + "\n");				
				sb.append(getString(R.string.msgBody)+ "\n" + 
						bundle.getString("msgBody")+ "\n"); 
				tvInfo.setText(sb);
				break;
			case 1: //代表有來電
				sb.append(getString(R.string.caller) + 
						bundle.getString("phoneNo") + "\n");
				tvInfo.setText(sb);
				break;
		}
	}
}
