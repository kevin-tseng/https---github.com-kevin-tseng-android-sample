package org.telephonyEx;

import static android.telephony.TelephonyManager.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class PhoneReceiver extends BroadcastReceiver {
	private final static int requestCode = 1; //來電請求碼設定為1
	@Override
	public void onReceive(Context context, Intent intent){
		Bundle bundle = intent.getExtras();
		String phoneNo = "";
		String phoneState = "";
		if(bundle != null)
			phoneState = bundle.getString(EXTRA_STATE);
		
		if(phoneState.equals(EXTRA_STATE_RINGING)){ 
			phoneNo = bundle.getString(EXTRA_INCOMING_NUMBER);
			Intent i = new Intent(context, Result.class);
			Bundle b = new Bundle();		
			b.putInt("requestCode", requestCode);
			b.putString("phoneNo", phoneNo);
			i.putExtras(b);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}
	}
}
