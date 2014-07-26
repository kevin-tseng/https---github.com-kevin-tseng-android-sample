package org.audioMgrEx;

import static android.media.AudioManager.*;
import static android.media.RingtoneManager.*;
import static android.provider.Settings.System.*;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class AudioMgrEx extends Activity {
	private TextView tvInfo; //顯示音量與靜音、震動狀態
	private RadioGroup rgVolume, rgVibrate; 
	private RadioButton rbRingtone; //鈴聲
	private RadioButton rbMusic; //音樂
	private RadioButton rbSystem; //系統聲音
	private RadioButton rbNormal; //正常
	private RadioButton rbSilent; //靜音
	private RadioButton rbVibrate; //震動
	private Button btnRaise, btnLower; //大聲、小聲按鈕
	private Ringtone testTone;
	private int streamType, direction;
	private AudioManager audioMgr;
	private Vibrator vibrator;
	private MediaPlayer mp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        audioMgr = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        findViews();        
    }

	private void findViews() {
		tvInfo = (TextView)findViewById(R.id.tvInfo);
		showInfo();
		
		OnCheckedChangeListener rgVolumeListener = new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(testTone != null && testTone.isPlaying())//如果正在播放鈴聲，將其停止
					testTone.stop();
				if(mp != null && mp.isPlaying())
					mp.stop();
				
				if(rbRingtone.isChecked()){ //選取「鈴聲」選項
					testTone = getRingtone( 
							AudioMgrEx.this, DEFAULT_RINGTONE_URI);
					streamType = STREAM_RING; 
				}
				else if(rbMusic.isChecked()){ //選取「音樂」選項
					mp = MediaPlayer.create(AudioMgrEx.this, R.raw.ring);
					streamType = STREAM_MUSIC; 
				}
				else{ //選取「系統聲音」選項
					streamType = STREAM_SYSTEM;
				}
			}
		};
		rgVolume = (RadioGroup)findViewById(R.id.rgVolume);
		rgVolume.setOnCheckedChangeListener(rgVolumeListener); 
		rbRingtone = (RadioButton)findViewById(R.id.rbRingtone);
		rbMusic = (RadioButton)findViewById(R.id.rbMusic);
		rbSystem = (RadioButton)findViewById(R.id.rbSystem);
		rbRingtone.setChecked(true);
				
		OnClickListener btnListener = new OnClickListener() {			
			@Override
			public void onClick(View v) {
				//按下「大聲」按鈕
				if(v == btnRaise) 
					direction = ADJUST_RAISE; 					
				
				//按下「小聲」按鈕
				else 
					direction = ADJUST_LOWER; 	
				
				if(rbRingtone.isChecked()){ 
					audioMgr.adjustStreamVolume(streamType, direction, 
							FLAG_SHOW_UI + FLAG_VIBRATE);	
					if(testTone != null && !testTone.isPlaying()) 
						testTone.play();
				}				
				else if(rbMusic.isChecked()){
					audioMgr.adjustStreamVolume(streamType, direction, 
							FLAG_SHOW_UI);				
					if(mp != null && !mp.isPlaying()) 
						mp.start();
				}				
				else{
					audioMgr.adjustStreamVolume(streamType, direction, 
							FLAG_SHOW_UI + FLAG_PLAY_SOUND);
				}
				showInfo();
			}
		};			
		btnRaise = (Button)findViewById(R.id.btnRaise);
		btnRaise.setOnClickListener(btnListener);
		btnLower = (Button)findViewById(R.id.btnLower);
		btnLower.setOnClickListener(btnListener);	
		
		rbNormal = (RadioButton)findViewById(R.id.rbNormal);
		rbSilent = (RadioButton)findViewById(R.id.rbSilent);
		rbVibrate = (RadioButton)findViewById(R.id.rbVibrate);
		switch(audioMgr.getRingerMode()){ 
			case RINGER_MODE_NORMAL:
				rbNormal.setChecked(true);
				break;
			case RINGER_MODE_SILENT:
				rbSilent.setChecked(true);
				break;
			case RINGER_MODE_VIBRATE:
				rbVibrate.setChecked(true);
				break;
		}
		
		OnCheckedChangeListener rgVibrateListener = new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				//選取「正常」選項
				if(rbNormal.isChecked()){ 
					audioMgr.setRingerMode(RINGER_MODE_NORMAL);
				}
				//選取「靜音」選項
				else if(rbSilent.isChecked()){ 
					audioMgr.setRingerMode(RINGER_MODE_SILENT);
				}
				//選取「震動」選項
				else{ 
					audioMgr.setRingerMode(RINGER_MODE_VIBRATE);
					vibrator.vibrate(200); 
				}
				showInfo();
			}
		};
		rgVibrate = (RadioGroup)findViewById(R.id.rgVibrate);
		rgVibrate.setOnCheckedChangeListener(rgVibrateListener);	
	}
	
	private void showInfo() {
		int volume_ringtone = audioMgr.getStreamVolume(STREAM_RING);
		int maxVolume_ringtone = audioMgr.getStreamMaxVolume(STREAM_RING);
		int volume_music = audioMgr.getStreamVolume(STREAM_MUSIC);
		int maxVolume_music = audioMgr.getStreamMaxVolume(STREAM_MUSIC);
		int volume_system = audioMgr.getStreamVolume(STREAM_SYSTEM);
		int maxVolume_system = audioMgr.getStreamMaxVolume(STREAM_SYSTEM);
		StringBuilder sbInfo = new StringBuilder();
		sbInfo.append(getString(R.string.curVol_maxVol) + "\n" +
			getString(R.string.ringtone) + ": " + 
				volume_ringtone + "/" + maxVolume_ringtone + "\n" +
			getString(R.string.music) + ": " + 
				volume_music + "/" + maxVolume_music + "\n" + 
			getString(R.string.system) + ": " + 
				volume_system + "/" + maxVolume_system + "\n"); 
		
		sbInfo.append(getString(R.string.ringerMode) + ": ");
		switch(audioMgr.getRingerMode()){ 
			case RINGER_MODE_NORMAL:
				sbInfo.append(getString(R.string.normal));
				break;
			case RINGER_MODE_SILENT:
				sbInfo.append(getString(R.string.silent));
				break;
			case RINGER_MODE_VIBRATE:
				sbInfo.append(getString(R.string.vibrate));
				break;
		}			
		tvInfo.setText(sbInfo);		
	}

	@Override
	protected void onPause() {		
		super.onPause();
		if(testTone != null && testTone.isPlaying())
			testTone.stop();
		if(mp != null && mp.isPlaying())
			mp.stop();
	}
}