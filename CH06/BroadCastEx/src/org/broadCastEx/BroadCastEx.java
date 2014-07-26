package org.broadCastEx;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class BroadCastEx extends Activity {
	private static final String BROADCAST_ACTION = "org.broadCastEx.BroadCastEx";
	private Button btnSend, btnCancel;
	private MyReceiver receiver;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findViews();
		registerMyReceiver();
	}
	
	private class MyReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(BroadCastEx.this, R.string.brocastReceived, 
					Toast.LENGTH_SHORT).show();
		}		
	}
		
	private void registerMyReceiver() {
		Toast.makeText(BroadCastEx.this, R.string.brocastRegister, 
				Toast.LENGTH_SHORT).show();
		IntentFilter filter = new IntentFilter(BROADCAST_ACTION);
		receiver = new MyReceiver();
		registerReceiver(receiver, filter);			
	}

	private void findViews() {
		btnSend = (Button) findViewById(R.id.btnSend);
		btnSend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(BroadCastEx.this, R.string.brocastSend, 
						Toast.LENGTH_SHORT).show();
				sendBroadcast(new Intent(BROADCAST_ACTION));
			}
		});
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				try{
					unregisterReceiver(receiver);
					Toast.makeText(BroadCastEx.this, R.string.brocastCancel, 
							Toast.LENGTH_SHORT).show();			
				}catch(IllegalArgumentException e){
					Toast.makeText(BroadCastEx.this, R.string.brocastCancelled, 
						Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	public void onDestroy(){
		super.onDestroy();
		try{
			unregisterReceiver(receiver);
		}catch(IllegalArgumentException e){
			Toast.makeText(BroadCastEx.this, R.string.brocastCancelled, 
					Toast.LENGTH_SHORT).show();
		}	
	}
}
