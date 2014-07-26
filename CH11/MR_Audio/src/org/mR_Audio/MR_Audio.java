package org.mR_Audio;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MR_Audio extends Activity {
	private final String tag = getClass().getName();
	private MediaRecorder mr; //錄音器
	private TextView tvInfo; //訊息方塊
	private Button btnRecord; //「錄音」按鈕
	private Button btnStopRecord; //「停止錄音」按鈕
	private Button btnPlay; //「播放」按鈕
	private Button btnStopPlay; //「停止播放」按鈕
	private String path = "/sdcard/audio01.3gp"; //錄音存檔路徑
	private MediaPlayer mp; //播放器
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findViews();
	}

	private void findViews() {
		tvInfo = (TextView)findViewById(R.id.tvInfo);
		btnRecord = (Button) findViewById(R.id.btnRecord);
		btnStopRecord = (Button) findViewById(R.id.btnStopRecord);
		btnPlay = (Button) findViewById(R.id.btnPlay);
		btnStopPlay = (Button) findViewById(R.id.btnStopPlay);
		
		//按下「錄音」按鈕
		btnRecord.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if(!isSDExist()){
					Toast.makeText(MR_Audio.this, 
							R.string.SDCardNotFound, Toast.LENGTH_LONG).show();
					return;
				}
				btnRecord.setEnabled(false); 
				recordAudio();
			}
		});
		
		//按下「停止錄音」按鈕
		btnStopRecord.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if(mr != null){
					mr.stop();
					mr.release();
					mr = null;
					tvInfo.setText(getString(R.string.filePath) + path);
					btnRecord.setEnabled(true); 
				}				
			}
		});		
		
		//按下「播放」按鈕
		btnPlay.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				playAudio(path);
			}
		});
		
		//按下「停止播放」按鈕
		btnStopPlay.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if (mp != null) {
					mp.stop();
				}
			}
		});
	}
	
	private void recordAudio() {
		if(mr == null)
			mr = new MediaRecorder();
		else
			mr.reset();
		try {
			mr.setAudioSource(MediaRecorder.AudioSource.MIC);
			mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mr.setOutputFile(path);				
			mr.prepare();
			mr.start();
		} catch (Exception e) {
			Log.e(tag, e.toString());
		}				
		tvInfo.setText(R.string.recording);		
	}

	private boolean isSDExist() { 
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED))
			return true;
		else			
			return false;		
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
			Log.e(tag, e.toString());
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