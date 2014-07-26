package org.alertDialogEx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AlertDialogEx extends Activity {
	private Button btnExit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
    }
	private void findViews() {
		btnExit = (Button)findViewById(R.id.btnExit);
		btnExit.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(AlertDialogEx.this)
				.setTitle(R.string.title)
				.setMessage(R.string.prompt)
				.setPositiveButton(R.string.submit,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							AlertDialogEx.this.finish();
						}
					})
				.setNegativeButton(R.string.cancel, 
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
						}
					})
				.setCancelable(false)
				.show();
			}
		});
	}	
}