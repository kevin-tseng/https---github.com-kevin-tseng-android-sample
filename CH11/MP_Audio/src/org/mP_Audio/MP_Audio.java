package org.mP_Audio;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MP_Audio extends Activity {
	private MediaPlayer mp; //播放器
	private TextView tvInfo; //訊息方塊
	private Button btnSDAudio; //「播放>>SD card Audio」按鈕
	private Button btnURLAudio; //「播放>>網路 Audio」按鈕
	private Button btnStop; //「停止」按鈕

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findViews();
	}

	private void findViews() {
		tvInfo = (TextView)findViewById(R.id.tvInfo);
		btnSDAudio = (Button)findViewById(R.id.btnSDAudio);
		btnURLAudio = (Button)findViewById(R.id.btnURLAudio);
		btnStop = (Button)findViewById(R.id.btnStop);
		
		//按下「播放>>SD card Audio」按鈕
		btnSDAudio.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				String path = "/sdcard/ring.mp3";
				playAudio(path);
			}
		});
		
		//按下「播放>>網路 Audio」按鈕
		btnURLAudio.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				String path = 
					"http://sites.google.com/site/ronforwork/Home/android-2/ring.mp3";
				playAudio(path);
			}
		});
		
		//按下「停止」按鈕
		btnStop.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if (mp != null) {
					mp.stop();
				}
			}
		});
	}

	private void playAudio(String path) {
		tvInfo.setText(getString(R.string.audioSource) + path);
		if(mp == null)
			mp = new MediaPlayer();
		try {
			mp.reset(); 
			mp.setDataSource(path);	
			mp.setAudioStreamType(AudioManager.STREAM_MUSIC); 
			mp.prepare(); 
			mp.start();
		} catch (Exception e) {
			Log.e("MP_Audio", e.toString());
		}		
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mp != null) {
			mp.release();
			mp = null;
		}
	}
}
