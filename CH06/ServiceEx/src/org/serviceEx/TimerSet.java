package org.serviceEx;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class TimerSet extends Activity {
	private EditText etTimer; 
	private Button btnSubmit, btnStop;
	private TimerReceiver receiver;
	private int sec;
	private boolean isActive = false; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
        resetLayout(isActive);
        registerTimerReceiver();
    }

	private void findViews() {
		etTimer = (EditText)findViewById(R.id.etTimer);	
		btnSubmit = (Button)findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) { 
				sec = Integer.parseInt(etTimer.getText().toString());
				Intent intent = new Intent(TimerSet.this, TimerService.class);
				Bundle bundle = new Bundle();
				bundle.putInt("sec", sec);
				intent.putExtras(bundle);
				startService(intent);
				
				isActive = true;
				resetLayout(isActive);
			}
		});
		btnStop = (Button)findViewById(R.id.btnStop);
		btnStop.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) { 
				Intent intent = new Intent(TimerSet.this, TimerService.class);
				stopService(intent);
				
				isActive = false;
				resetLayout(isActive);
			}
		});		
	}
	
	private void resetLayout(boolean isActive){ 
		if(isActive){
			btnSubmit.setVisibility(View.INVISIBLE);
			btnStop.setVisibility(View.VISIBLE);
		}else{
			btnSubmit.setVisibility(View.VISIBLE);
			btnStop.setVisibility(View.INVISIBLE);
		}
	}

	private void registerTimerReceiver() {
		IntentFilter filter = new IntentFilter(TimerService.TIMER_ACTION);
		receiver = new TimerReceiver();
		registerReceiver(receiver, filter);			
	}

	private class TimerReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {			
			MediaPlayer mp = MediaPlayer.create(TimerSet.this, R.raw.ring);
			mp.start();
		}		
	}
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}
}