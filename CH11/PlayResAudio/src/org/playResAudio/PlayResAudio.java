package org.playResAudio;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlayResAudio extends Activity{
	private MediaPlayer mp; //播放器
	private TextView tvInfo; //訊息方塊
	private Button btnPlay; //「播放」按鈕
	private Button btnPause; //「暫停」按鈕
	private Button btnStop; //「停止」按鈕
	private boolean isStoped = true; //true代表處於完全停止播放狀態

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findViews();
	}

	private void findViews() {
		tvInfo = (TextView)findViewById(R.id.tvInfo);
		btnPlay = (Button) findViewById(R.id.btnPlay);
		btnPause = (Button) findViewById(R.id.btnPause);
		btnStop = (Button) findViewById(R.id.btnStop);
		
		//按下「播放」按鈕
		btnPlay.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if(mp == null || isStoped){
					mp = MediaPlayer.create(PlayResAudio.this, R.raw.ring);
					isStoped = false;
				}
				
				tvInfo.setText(getString(R.string.audioSource) + 
						getString(R.raw.ring));
				mp.start();				
			}
		});
		
		//按下「暫停」按鈕
		btnPause.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if(mp == null || isStoped)
					return;
				mp.pause();
			}
		});

		//按下「停止」按鈕
		btnStop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if(mp == null || isStoped)
					return;
				mp.stop();
				isStoped = true;
			}
		});		
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