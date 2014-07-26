package org.notificationEx;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NotificationEx extends Activity {
	private final static int NOTIFICATION_ID = 0;
	private Button btnNtf, btnCancel;
	private NotificationManager ntfMgr;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
		ntfMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);		
	}

	private void findViews() {
		btnNtf = (Button)findViewById(R.id.btnNtf);
		btnNtf.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				showNotification();				
			}
		});
		btnCancel = (Button)findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				//ntfMgr.cancelAll(); 
				ntfMgr.cancel(NOTIFICATION_ID); 
			}
		});
	}

	private void showNotification() {		
		Notification ntf = new Notification(
				android.R.drawable.ic_menu_info_details,  
				getString(R.string.tickerText), 
				System.currentTimeMillis()); 		
		PendingIntent contentIntent = PendingIntent.getActivity( 
				this, 0, new Intent(this, NotificationEx.class), 0); 
		ntf.setLatestEventInfo(this,  
				getString(R.string.contentTitle), 
				getString(R.string.contentText), 
				contentIntent);		
		ntfMgr.notify(NOTIFICATION_ID, ntf);
	}	
}