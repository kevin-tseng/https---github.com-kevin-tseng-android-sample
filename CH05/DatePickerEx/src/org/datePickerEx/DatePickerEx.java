package org.datePickerEx;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickerEx extends Activity {
    private TextView tvDateDisplay;
    private Button btnPickDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID = 0;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();         
        final Calendar c = Calendar.getInstance(); 
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        updateDisplay();
    }

	private void updateDisplay() {
		tvDateDisplay.setText(
			new StringBuilder()
					.append(mYear).append("-")					
					.append(mMonth + 1).append("-")
					.append(mDay));
	}
	
	private void findViews() {
		tvDateDisplay = (TextView)findViewById(R.id.tvDateDisplay);
		btnPickDate = (Button)findViewById(R.id.btnPickDate);
		btnPickDate.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);				
			}
		});
	}

	private DatePickerDialog.OnDateSetListener dateSetListener =
			new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year, 
									  int monthOfYear, int dayOfMonth) {
					mYear = year;
					mMonth = monthOfYear;
					mDay = dayOfMonth;
					updateDisplay();
				}
			};
	
	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, 
					dateSetListener, 
					mYear, mMonth, mDay);
		}
		return null;
	}
}