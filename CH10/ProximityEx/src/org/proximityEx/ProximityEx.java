package org.proximityEx;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProximityEx extends Activity {
	private SensorManager sensorMgr;
	private TextView tvMsg;
	private LinearLayout linear;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		sensorMgr = (SensorManager)getSystemService(SENSOR_SERVICE);
		findViews();		
	}
	
	private void findViews() {
		tvMsg = (TextView)findViewById(R.id.tvMsg);
		linear = (LinearLayout)findViewById(R.id.linear);
	}
	
	SensorEventListener listener = new SensorEventListener() {		
		public void onSensorChanged(SensorEvent event) {
			Sensor sensor = event.sensor;
			float[] values = event.values;
			StringBuilder sensorInfo = new StringBuilder();
			sensorInfo.append("sensor name: " + sensor.getName() + "\n");
			sensorInfo.append("sensor type: " + sensor.getType() + "\n");
			sensorInfo.append("used power: " + sensor.getPower() + " mA\n");
			sensorInfo.append("sensor maximum range: " + 
						sensor.getMaximumRange() + "\n");
			sensorInfo.append("values[0] = " + values[0] + "\n");			
			tvMsg.setText(sensorInfo);
			
			if(values[0] < 1.0)
				linear.setBackgroundColor(Color.rgb(0, 0, 68));
			else
				linear.setBackgroundColor(Color.rgb(68, 0, 0));			
		}
		
		public void onAccuracyChanged(Sensor sensor, int accuracy) {}
	};

	@Override
	protected void onResume() {
		super.onResume();				
		sensorMgr.registerListener(listener, 
					sensorMgr.getDefaultSensor(Sensor.TYPE_PROXIMITY),
					SensorManager.SENSOR_DELAY_UI);		
	}

	@Override
	protected void onPause() {
		super.onPause();		
		sensorMgr.unregisterListener(listener);
	}
}
