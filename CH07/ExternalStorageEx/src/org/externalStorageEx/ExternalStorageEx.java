package org.externalStorageEx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.app.Activity;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ExternalStorageEx extends Activity {
	private ImageView ivPhoto;
    private Button btnSavePublic;
    private Button btnSavePrivate;
    private TextView tvMsg;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
    }

	private void findViews() {		
		ivPhoto = (ImageView)findViewById(R.id.ivPhoto);
		ivPhoto.setImageDrawable(
				getResources().getDrawable(R.drawable.photo));
		btnSavePublic = (Button)findViewById(R.id.btnSavePublic);
		btnSavePrivate = (Button)findViewById(R.id.btnSavePrivate);
		tvMsg = (TextView)findViewById(R.id.tvMsg);
		
		//按下「儲存至公開照片區」按鈕
		btnSavePublic.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
		        File path = Environment.getExternalStoragePublicDirectory(
		                Environment.DIRECTORY_PICTURES);
		        File file = new File(path, "photo.jpg");
		        createFile(file);
			}
		});	
		
		//按下「儲存至私有照片區」按鈕
		btnSavePrivate.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {	
				File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		        File file = new File(path, "photo.jpg");
				createFile(file);
			}
		});	
		
	}
	
	protected void createFile(File file) {
		File parentPath = file.getParentFile();
		if (!isSDExist()){
			Toast.makeText(this, 
					R.string.SDCardNotFound, Toast.LENGTH_LONG).show();
			return;
		}
        try {
            if(!parentPath.exists())
            	parentPath.mkdirs();
            if(file.exists())
            	file.delete();
            InputStream is = getResources().openRawResource(R.drawable.photo);
            OutputStream os = new FileOutputStream(file);
            byte[] data = new byte[is.available()];
            is.read(data);
            os.write(data);
            tvMsg.setText(getString(R.string.saveFileTo) + file.toString());
            is.close();
            os.close();  
        } catch (IOException e) {
            Log.e("ExternalStorageEx", e.toString());
        }	
        
        String[] paths = {file.toString()};
        callMediaScanner(paths);
	}

	private boolean isSDExist() { 
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED) || 
			state.equals(Environment.MEDIA_MOUNTED_READ_ONLY))
			return true;
		else			
			return false;		
	}
	
	private void callMediaScanner(String[] paths) {
        MediaScannerConnection.scanFile(this, paths, null,
        	new MediaScannerConnection.OnScanCompletedListener() {
            public void onScanCompleted(String path, Uri uri) {
                Log.i("ExternalStorageEx", "Scanned " + path + ":");
                Log.i("ExternalStorageEx", "-> uri=" + uri);
            }
        });		
	}
}