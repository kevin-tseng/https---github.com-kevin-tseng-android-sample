package org.internalStorageEx;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InternalStorageEx extends Activity {
    private EditText etNote;
    private TextView tvNote;
    private Button btnSave;
    private Button btnAppend;
    private Button btnOpen;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
    }

	private void findViews() {
		etNote = (EditText)findViewById(R.id.etNote);
		tvNote = (TextView)findViewById(R.id.tvNote);
		btnSave = (Button)findViewById(R.id.btnSave);
		btnAppend = (Button)findViewById(R.id.btnAppend);
		btnOpen = (Button)findViewById(R.id.btnOpen);		
		
		//按下「儲存檔案」按鈕
		btnSave.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				FileOutputStream fos = null;
				try {
					fos = openFileOutput("note.txt", Context.MODE_PRIVATE);
					fos.write(etNote.getText().toString().getBytes());
					fos.close();
				} catch (IOException e) {
					Log.e("InternalStorageEx", e.toString());
				}	
				tvNote.setText(getText(R.string.fileSaved));
			}
		});	
		
		//按下「附加後存檔」按鈕
		btnAppend.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				FileOutputStream fos = null;
				try {
					fos = openFileOutput("note.txt", Context.MODE_APPEND);
					fos.write(etNote.getText().toString().getBytes());
					fos.close();
				} catch (IOException e) {
					Log.e("InternalStorageEx", e.toString());
				}	
				tvNote.setText(getText(R.string.fileAppended));
			}
		});	
		
		//按下「開啟檔案」按鈕
		btnOpen.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				FileInputStream fis = null;
				StringBuilder sb = new StringBuilder();
				try {
					fis = openFileInput("note.txt");
					InputStreamReader isr = new InputStreamReader(fis);
					BufferedReader br = new BufferedReader(isr);
					String str = "";
					while((str = br.readLine())!=null){
						sb.append(str);
					}
					br.close();
					isr.close();
					fis.close();
				} catch (IOException e) {
					Log.e("InternalStorageEx", e.toString());
				}
				tvNote.setText(sb);
			}
		});	
	}
}