package org.buttonEx;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ButtonEx extends Activity {
	private Button btnSubmit;
	private Button cusbutton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
    }

	private void findViews() {
		btnSubmit = (Button)findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				CharSequence text = ((Button)v).getText();
				Toast.makeText(
						ButtonEx.this, 
						text, 
						Toast.LENGTH_SHORT)
						.show();			
			}
		});
		
		cusbutton = (Button)findViewById(R.id.cusbutton);
		cusbutton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Toast.makeText(
						ButtonEx.this, 
						R.string.imagebutton_pressed, 
						Toast.LENGTH_SHORT)
						.show();				
			}
		});
	}
}