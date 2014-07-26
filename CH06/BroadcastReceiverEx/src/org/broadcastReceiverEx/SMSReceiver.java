package org.broadcastReceiverEx;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent){
		Toast.makeText(context, 
				R.string.receivedSMS, Toast.LENGTH_LONG).show();
	}
}
