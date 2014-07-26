package org.seekBarEx;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SeekBarEx extends Activity {
	private LinearLayout linear;
	private SeekBar sbColor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
    }
	private void findViews() {
		linear = (LinearLayout)findViewById(R.id.linear);
		sbColor = (SeekBar)findViewById(R.id.sbColor);
		sbColor.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {						
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				linear.setBackgroundColor(
						Color.rgb(progress, progress, progress));				
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				String msg = getString(R.string.color_value) + seekBar.getProgress();
				Toast.makeText(
						SeekBarEx.this, 
						msg, 
						Toast.LENGTH_SHORT)
						.show();					
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				//Notification that the user has started a touch gesture.
			}
		});
	}
}