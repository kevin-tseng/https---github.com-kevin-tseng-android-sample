package org.inputEx;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputEx extends Activity {
	private EditText etId;
	private EditText etName;
	private EditText etMobile;
	private Button btnSubmit;
   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();        
    }
	private void findViews() {
		etId = (EditText)findViewById(R.id.etId);
		etName = (EditText)findViewById(R.id.etName);
		etMobile = (EditText)findViewById(R.id.etMobile);
		btnSubmit = (Button)findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				String id = etId.getText().toString();
				String name = etName.getText().toString();
				String mobile = etMobile.getText().toString();
				String msg = 
					getString(R.string.yourInfo) + id + "\n" + name + "\n" + mobile;
				Toast.makeText(
						InputEx.this, 
						msg, 
						Toast.LENGTH_LONG)
						.show();
			}
		});
	}
}