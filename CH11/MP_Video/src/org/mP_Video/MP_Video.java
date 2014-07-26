package org.mP_Video;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MP_Video extends Activity {
	private final String tag = getClass().getName(); //取得類別名稱當作Log使用的標籤
	private int videoWidth; //video寬度
	private int videoHeight; //video高度
	private MediaPlayer mp; //撥放器
	private SurfaceView svScreen; //SurfaceView元件，用來呈現video畫面
	private SurfaceHolder sHolder; //控制surface的SurfaceHolder元件
	private String path = "/sdcard/littleMonster.3gp"; //video路徑
	private boolean isVideoSizeKnown = false; //是否取得video尺寸
	private boolean isVideoReady = false; //video是否準備妥當

	class SurfaceCallback implements SurfaceHolder.Callback{
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			initMediaPlayer();
		}
		
		@Override
		public void surfaceChanged(SurfaceHolder holder, 
				int format, int width, int height) {
			Log.d(tag, "surfaceChanged called");
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder surfaceholder) {
			releaseMediaPlayer();
			doCleanUp();
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findViews();
	}

	private void findViews() { 		
		svScreen = (SurfaceView) findViewById(R.id.svScreen);
		sHolder = svScreen.getHolder();
		sHolder.addCallback(new SurfaceCallback());
		sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);		
	}

	private void initMediaPlayer() {
		doCleanUp();
		if(mp == null)
			mp = new MediaPlayer();
		try {
			mp = new MediaPlayer();
			mp.setDataSource(path);
			mp.setDisplay(sHolder);
			mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mp.prepare();
			mp.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {				
				@Override
				public void onBufferingUpdate(MediaPlayer mp, int percent) {
					Log.d(tag, "onBufferingUpdate percent:" + percent);
				}
			});
			mp.setOnCompletionListener(new OnCompletionListener() {				
				@Override
				public void onCompletion(MediaPlayer mp) {
					Log.d(tag, "onCompletion called");
				}
			});
			mp.setOnPreparedListener(new OnPreparedListener() {				
				@Override
				public void onPrepared(MediaPlayer mp) {
					isVideoReady = true;
					if (isVideoSizeKnown) {
						playVideo();
					}
				}
			});
			mp.setOnVideoSizeChangedListener(new OnVideoSizeChangedListener() {				
				@Override
				public void onVideoSizeChanged(
						MediaPlayer mp, int width, int height) {
					if (width == 0 || height == 0) {
						Log.e(tag, 
							"invalid video width(" + width + ") or height(" + height
								+ ")");
						return;
					}
					isVideoSizeKnown = true;
					videoWidth = width;
					videoHeight = height;
					if (isVideoReady) {
						playVideo();
					}
				}
			});
		} catch (Exception e) {
			Log.e(tag, e.toString());
		}
	}

	private void playVideo() {
		sHolder.setFixedSize(videoWidth, videoHeight);
		mp.start();
	}

	private void releaseMediaPlayer() {
		if (mp != null) {
			mp.release();
			mp = null;
		}
	}

	private void doCleanUp() {
		videoWidth = 0;
		videoHeight = 0;
		isVideoReady = false;
		isVideoSizeKnown = false;
	}
}