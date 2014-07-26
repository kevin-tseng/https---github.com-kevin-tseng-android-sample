package org.ringtoneEx;

import static android.media.RingtoneManager.*;
import static android.provider.Settings.System.*;
import android.app.Activity;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RingtoneEx extends Activity {
	private TextView tvInfo; //顯示各種類的現行鈴聲
	private Button btnRingtone; //「電話鈴聲」按鈕
	private Button btnNotification; //「通知鈴聲」按鈕
	private Button btnAlarm; //「鬧鐘鈴聲」按鈕
	private final static int req_ringtone = 0; //代表電話鈴聲挑選器
	private final static int req_notification = 1; //代表通知鈴聲挑選器
	private final static int req_alarm = 2; //代表鬧鐘鈴聲挑選器
	private RingtoneManager ringtoneMgr; 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ringtoneMgr = new RingtoneManager(this);
        findViews();        
    }

	private void findViews() {
		tvInfo = (TextView)findViewById(R.id.tvInfo);
		showRingtone();
		OnClickHandler handler = new OnClickHandler();
		btnRingtone = (Button)findViewById(R.id.btnRingtone);
		btnRingtone.setOnClickListener(handler);
		btnNotification = (Button)findViewById(R.id.btnNotification);
		btnNotification.setOnClickListener(handler);
		btnAlarm = (Button)findViewById(R.id.btnAlarm);
		btnAlarm.setOnClickListener(handler);		
	}
	
	private void showRingtone() {
		Ringtone phone = getRingtone(this, DEFAULT_RINGTONE_URI);
		Ringtone notification = getRingtone(this, DEFAULT_NOTIFICATION_URI);
		Ringtone alarm = getRingtone(this, DEFAULT_ALARM_ALERT_URI);
		StringBuilder sbInfo = new StringBuilder();
		sbInfo.append(getString(R.string.ringtone_title) + ": ");		
		if(phone != null) 
			sbInfo.append(phone.getTitle(this));
		sbInfo.append("\n");		
		sbInfo.append(getString(R.string.notification_title) + ": ");
		if(notification != null)
			sbInfo.append(notification.getTitle(this));
		sbInfo.append("\n");
		sbInfo.append(getString(R.string.alarm_title) + ": ");
		if(alarm != null)
			sbInfo.append(alarm.getTitle(this));
		tvInfo.setText(sbInfo);		
	}

	class OnClickHandler implements OnClickListener{
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(ACTION_RINGTONE_PICKER);
			//按下「電話鈴聲」按鈕
			if(v == btnRingtone){				
				intent.putExtra(EXTRA_RINGTONE_TITLE, 
						getString(R.string.ringtone_title)); 
				intent.putExtra(EXTRA_RINGTONE_SHOW_SILENT, false); 	
				intent.putExtra(EXTRA_RINGTONE_EXISTING_URI, 
						getActualDefaultRingtoneUri(
								RingtoneEx.this, TYPE_RINGTONE)); 		        
		        startActivityForResult(intent, req_ringtone);	
			}
			//按下「通知鈴聲」按鈕
			else if(v == btnNotification){
				intent.putExtra(EXTRA_RINGTONE_TITLE, 
						getString(R.string.notification_title)); 
				intent.putExtra(EXTRA_RINGTONE_TYPE, TYPE_NOTIFICATION); 
				intent.putExtra(EXTRA_RINGTONE_DEFAULT_URI, 
						DEFAULT_NOTIFICATION_URI); 
				intent.putExtra(EXTRA_RINGTONE_SHOW_SILENT, false); 
				intent.putExtra(EXTRA_RINGTONE_EXISTING_URI, 
						getActualDefaultRingtoneUri(
							RingtoneEx.this, TYPE_NOTIFICATION)); 
		        startActivityForResult(intent, req_notification);	
			}
			//按下「鬧鐘鈴聲」按鈕
			else{
				intent.putExtra(EXTRA_RINGTONE_TITLE, 
						getString(R.string.alarm_title)); 
				intent.putExtra(EXTRA_RINGTONE_TYPE, TYPE_ALARM); 
				intent.putExtra(EXTRA_RINGTONE_DEFAULT_URI, 
						DEFAULT_ALARM_ALERT_URI); 
				intent.putExtra(EXTRA_RINGTONE_SHOW_SILENT, false); 
				intent.putExtra(EXTRA_RINGTONE_EXISTING_URI, 
						getActualDefaultRingtoneUri(
								RingtoneEx.this, TYPE_ALARM)); 
		        startActivityForResult(intent, req_alarm);
			}			
		}		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if(resultCode != RESULT_OK) return;
		Uri uri = intent.getParcelableExtra(EXTRA_RINGTONE_PICKED_URI);
		if(uri != null){
			switch(requestCode){
				case req_ringtone:
					setActualDefaultRingtoneUri(this, TYPE_RINGTONE, uri); //manifest add - android.permission.WRITE_SETTINGS
					break;
				case req_notification:
					setActualDefaultRingtoneUri(this, TYPE_NOTIFICATION, uri);
					break;
				case req_alarm:
					setActualDefaultRingtoneUri(this, TYPE_ALARM, uri);
					break;			
			}
			showRingtone();
		}
	}

	@Override
	protected void onPause() {		
		super.onPause();
		ringtoneMgr.stopPreviousRingtone();
	}
}