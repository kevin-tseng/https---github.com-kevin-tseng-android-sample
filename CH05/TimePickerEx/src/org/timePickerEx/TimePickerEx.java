package org.timePickerEx;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class TimePickerEx extends Activity {
    private TextView tvTimeDisplay;
    private Button btnPickTime;
    private int mHour;
    private int mMinute;
    static final int TIME_DIALOG_ID = 0;    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();        
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);        
        updateDisplay();
    }
	
	private void updateDisplay() {
	    tvTimeDisplay.setText(
	        new StringBuilder()
	                .append(pad(mHour)).append(":")
	                .append(pad(mMinute)));
	}

	private static String pad(int c) {
	    if (c >= 10)
	        return String.valueOf(c);
	    else
	        return "0" + String.valueOf(c);
	}
	
	private void findViews() {
		tvTimeDisplay = (TextView)findViewById(R.id.tvTimeDisplay);
		btnPickTime = (Button)findViewById(R.id.btnPickTime);
		btnPickTime.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);				
			}
		});
	}

	private TimePickerDialog.OnTimeSetListener timeSetListener =
	    new TimePickerDialog.OnTimeSetListener() {
	        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	            mHour = hourOfDay;
	            mMinute = minute;
	            updateDisplay();
	        }
	    };
	    
    @Override
    protected Dialog onCreateDialog(int id, Bundle args) {
        switch (id) {
        case TIME_DIALOG_ID:
            return new TimePickerDialog(this,
                    timeSetListener, mHour, mMinute, false);
        }
        return null;
    }
}