package org.serviceBindEx;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TimerSet extends Activity {
	private EditText etTimer; 
	private Button btnSubmit, btnStop;
	private TimerReceiver receiver;
	private int sec;
    private boolean isBound;
    private TimerService timerService;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
        registerTimerReceiver();
    }

	private void findViews() {
		etTimer = (EditText)findViewById(R.id.etTimer);	
		btnSubmit = (Button)findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) { 
				doBindService();
			}
		});
		btnStop = (Button)findViewById(R.id.btnStop);
		btnStop.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) { 
				doUnbindService();				
			}
		});		
	}
    
    void doBindService() {
		sec = Integer.parseInt(etTimer.getText().toString());
		Intent intent = new Intent(TimerSet.this, TimerService.class);
		Bundle bundle = new Bundle();
		bundle.putInt("sec", sec);
		intent.putExtras(bundle);
        bindService(intent, serviceCon, Context.BIND_AUTO_CREATE);
        isBound = true;
    }
    
    void doUnbindService() {
        if (isBound) {
            unbindService(serviceCon);
            isBound = false;
        }
    }   
    
    private ServiceConnection serviceCon = new ServiceConnection() {
    	@Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            timerService = ((TimerService.TimerBinder)service).getService();
            Toast.makeText(TimerSet.this, "TimerServiceConnected",
                    Toast.LENGTH_SHORT).show();
        }
    	
    	@Override
        public void onServiceDisconnected(ComponentName className) {
            timerService = null;
            Toast.makeText(TimerSet.this, "TimerServiceDisconnected",
                    Toast.LENGTH_SHORT).show();
        }
    };
     
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
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
		doUnbindService();
	}
}