package org.selectButtons;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SelectButtons extends Activity {
	private CheckBox cbPlace;
	private RadioButton rbMale, rbFemale;
	private ToggleButton tbVibrate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
    }
    
	private void findViews() {
		cbPlace = (CheckBox)findViewById(R.id.cbPlace);
		cbPlace.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				String msg = cbPlace.getText().toString();
				if(((CheckBox)v).isChecked())
					msg = getString(R.string.checked) + " " + msg;
				else{
					msg = getString(R.string.unchecked) + " " + msg;
				}
				Toast.makeText(
						SelectButtons.this, 
						msg, 
						Toast.LENGTH_SHORT)
						.show();
			}
		});

		rbFemale = (RadioButton)findViewById(R.id.rbFemale);
		rbMale = (RadioButton)findViewById(R.id.rbMale);		
		OnClickListener rbListener = new OnClickListener() {			
			@Override
			public void onClick(View v) {
				RadioButton rb = (RadioButton)v;
		        Toast.makeText(
		        		SelectButtons.this, 
		        		rb.getText(), 
		        		Toast.LENGTH_SHORT)
		        		.show();
			}
		};		
		rbFemale.setOnClickListener(rbListener);
		rbMale.setOnClickListener(rbListener);
		
		tbVibrate = (ToggleButton)findViewById(R.id.tbVibrate);
		tbVibrate.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				ToggleButton tb = (ToggleButton)v;
		        Toast.makeText(
		        		SelectButtons.this, 
		        		tb.getText(), 
		        		Toast.LENGTH_SHORT)
		        		.show();
			}
		});		
	}
}