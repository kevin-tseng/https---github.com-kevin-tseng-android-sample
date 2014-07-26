package org.mR_Video;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MR_Video extends Activity {
	private final String tag = getClass().getName();
	private Camera camera;
	private SurfaceView svPreview;
	private SurfaceHolder sHolder;
	private boolean isPreview = false;
	private boolean isRecord = false; //是否錄影中
	private MediaRecorder mr;
	private CamcorderProfile profile; //錄影設定檔
	int videoWidth; //video寬度
	int videoHeight; //video高度
	private final int maxDurationInMs = 10 * 60 * 1000; //錄影時間限制
	private final long maxFileSizeInBytes = 10 * 1024 * 1024; //錄影檔案大小限制
	private String path = "/sdcard/video01.3gp";

	class SurfaceCallback implements SurfaceHolder.Callback{
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			if(camera == null)
				camera = Camera.open(); 
		}
		
		@Override
		public void surfaceChanged(SurfaceHolder holder, 
				int format, int width, int height) {	
			Camera.Parameters params = camera.getParameters();
			params.setPreviewSize(width, height);
			videoWidth = width;
			videoHeight = height;
			camera.setParameters(params);			
			try {
				camera.setPreviewDisplay(holder); 				 
				startPreview();
			} catch (IOException e) {
				Log.e(tag, e.toString());
			}
		}
		
		@Override 
		public void surfaceDestroyed(SurfaceHolder holder) {
			stopPreview();
			if(mr != null){
				mr.release();
				mr = null;
			}
			if(camera != null){ 
				camera.release();
				camera = null;
			}
		}		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setFullScreen();
		setContentView(R.layout.main); 
		findViews();
	}

	private void setFullScreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); 
	}

	private void findViews() {		
		svPreview = (SurfaceView)findViewById(R.id.svPreview);
		sHolder = svPreview.getHolder();
		sHolder.addCallback(new SurfaceCallback());
		sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);			
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_CAMERA
				|| keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			if(!isSDExist()){
				Toast.makeText(MR_Video.this, 
						R.string.SDCardNotFound, Toast.LENGTH_LONG).show();
				return true;
			}
			
			if(isRecord)
				stopRecord();
			else
				startRecord();
			return true;
		}
		return false;
	}

	private void startRecord() {
		startPreview();
		if(mr == null)
			mr = new MediaRecorder();
		else
			mr.reset();		
		camera.unlock();
		mr.setCamera(camera);
		profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
		profile.videoFrameWidth = videoWidth; 
		profile.videoFrameHeight = videoHeight;
		try {
			mr.setAudioSource(MediaRecorder.AudioSource.MIC);
			mr.setVideoSource(MediaRecorder.VideoSource.CAMERA);
			mr.setProfile(profile);
			mr.setOutputFile(path);
			mr.setMaxDuration(maxDurationInMs); 
			mr.setPreviewDisplay(sHolder.getSurface());
			mr.setMaxFileSize(maxFileSizeInBytes); 
			mr.prepare();
		} catch (Exception e) {
			Log.e(tag, e.toString());			
		}
		mr.start();
		isRecord = true;
		Toast.makeText(this, R.string.startRecord, 
				Toast.LENGTH_SHORT).show();		
	}
		
	private void stopRecord() {
		if(mr != null){
			Toast.makeText(this, R.string.stopRecord, 
					Toast.LENGTH_SHORT).show();
			Toast.makeText(this, 
					getString(R.string.filePath) + path.toString(), 
					Toast.LENGTH_LONG).show();
			mr.stop();
			isRecord = false;
			mr.release();
			mr = null;
			try {
				camera.reconnect();
			} catch (IOException e) {
				Log.e(tag, e.toString());
			}
			stopPreview();
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					startPreview();
				}
			};
			Timer timer = new Timer();
			timer.schedule(task, 5 * 1000);
		}		
	}

	private void startPreview(){
		if(camera != null && !isPreview){
			camera.startPreview();
			isPreview = true;
		}
	}

	private void stopPreview(){
		if(camera != null && isPreview){
			camera.stopPreview();
			isPreview = false;
		}
	}
	
	private boolean isSDExist() { 
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED))
			return true;
		else			
			return false;		
	}
}