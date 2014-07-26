package org.cameraEx;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.cameraEx.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class CameraEx extends Activity {
    private final String tag = getClass().getName();
	private Camera camera; //相機
	private SurfaceView svPreview; //相機預覽
	private SurfaceHolder sHolder;
	private Bitmap image; //照片影像
	private boolean isPreview = false; //是否在預覽狀態
	private String path = "/sdcard/photo01.jpg";

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
			List<Size> sizes = params.getSupportedPictureSizes();
			Size maxSize = params.getPictureSize(); 
			for(Size size: sizes){ 
				maxSize = 
					maxSize.width * maxSize.height > 
						size.width * size.height ? maxSize : size;			
			}
			params.setPictureSize(maxSize.width, maxSize.height);
			params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO); 
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
			if(camera != null){ 
				camera.release();
				camera = null;
			}
		}		
	}

	class AutoFocusCallback implements Camera.AutoFocusCallback{
		@Override
		public void onAutoFocus(boolean success, Camera camera) {
			if (success) {
				camera.takePicture(null, null, jpeg);
				isPreview = false; 
			}
		}		
	}

	Camera.PictureCallback jpeg = new Camera.PictureCallback() {		
		public void onPictureTaken(byte[] imgData, Camera camera) { 
			if (imgData != null) {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 1; 
				image = BitmapFactory.decodeByteArray(imgData, 0,
						imgData.length, options);
			}
		}
	};

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

	//按下實體相機按鈕
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
		if (keyCode == KeyEvent.KEYCODE_CAMERA
				|| keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			if(isPreview)
				camera.autoFocus(new AutoFocusCallback());
			return true;
		}
		return false;
	}
	
	private void startPreview() {
		if(camera != null && !isPreview){
			camera.startPreview();
			isPreview = true;
			image = null; 
			System.gc(); 
		}
	}
	
	private void stopPreview(){
		if(camera != null && isPreview){
			camera.stopPreview();
			isPreview = false;
		}
	}
	
	private void saveImage(File imageFile){
		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(imageFile));			
			image.compress(Bitmap.CompressFormat.JPEG, 90, bos);
			bos.flush();
			bos.close();
			Toast.makeText(this, 
					getString(R.string.filePath) + imageFile.toString(), 
					Toast.LENGTH_LONG).show();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
		
	private boolean isSDExist() { //判斷記憶卡是否存在
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			Toast.makeText(this, 
					R.string.SDCardNotFound, Toast.LENGTH_LONG).show();
			return false;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.cameramenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.save:
			if(isPreview){ 
				Toast.makeText(this, 
						R.string.photoNotTaken, Toast.LENGTH_SHORT).show();		
				return true;
			}
			if (isSDExist()){
				File imageFile = new File(path);
				saveImage(imageFile);
			}
			startPreview();
			break;
		case R.id.cancel:
			startPreview();
			break;
		case R.id.exit:
			this.finish();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
}