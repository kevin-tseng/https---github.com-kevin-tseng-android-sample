package org.editTextEx;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.Toast;

public class EditTextEx extends Activity {
    private EditText etInput;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
    }

	private void findViews() {
		etInput = (EditText)findViewById(R.id.etInput);
		etInput.setOnKeyListener(new OnKeyListener() {			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_DOWN &&
					keyCode == KeyEvent.KEYCODE_ENTER){
					Toast.makeText(
							EditTextEx.this, 
							etInput.getText(), 
							Toast.LENGTH_SHORT)
							.show();
					return true;
				}
				return false;
			}
		});
	}
}