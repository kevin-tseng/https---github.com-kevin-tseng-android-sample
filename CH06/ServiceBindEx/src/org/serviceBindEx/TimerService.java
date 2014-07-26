package org.serviceBindEx;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class TimerService extends Service {
	public static final String TIMER_ACTION = "org.serviceBindEx.TimerService";
	private Timer timer;
	private int sec;
	private NotificationManager ntfMgr;
	private final IBinder timerBinder = new TimerBinder();

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		Bundle bundle = intent.getExtras();
		sec = bundle.getInt("sec");
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				sendBroadcast(new Intent(TIMER_ACTION));
			}
		};
		timer = new Timer();
		timer.schedule(task, sec * 1000);
		ntfMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);		
		showNotification();
		return timerBinder;
	}

	public class TimerBinder extends Binder {
		TimerService getService() {
			return TimerService.this;
		}
	}

	@Override
	public boolean onUnbind(Intent intent) {
		ntfMgr.cancelAll();
		Toast.makeText(this, "onUnbind", Toast.LENGTH_SHORT).show();
		return true;
	}

	private void showNotification() {
		Notification ntf = new Notification(
				android.R.drawable.ic_menu_info_details, 
				sec + getString(R.string.playAfterSec), 
				System.currentTimeMillis());
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, TimerSet.class), 0);
		ntf.setLatestEventInfo( 
				this, 
				getString(R.string.playMusic), getString(R.string.speakerOn), 
				contentIntent);
		ntfMgr.notify(R.string.app_name, ntf);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		timer.cancel(); 
		ntfMgr.cancelAll();
	}
}
