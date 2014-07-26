package org.accelerometerEx;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class AccelerometerEx extends Activity {
	private SensorManager sensorMgr;
	private TextView tvMsg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		sensorMgr = (SensorManager)getSystemService(SENSOR_SERVICE);
		findViews();		
	}
	
	private void findViews() {
		tvMsg = (TextView)findViewById(R.id.tvMsg);		
	}
	
	SensorEventListener listener = new SensorEventListener() {		
		public void onSensorChanged(SensorEvent event) {
			Sensor sensor = event.sensor;
			StringBuilder sensorInfo = new StringBuilder();
			sensorInfo.append("sensor name: " + sensor.getName() + "\n");
			sensorInfo.append("sensor type: " + sensor.getType() + "\n");
			sensorInfo.append("used power: " + sensor.getPower() + " mA\n");
			sensorInfo.append("values: \n");
			float[] values = event.values;
			for (int i = 0; i < values.length; i++)
				sensorInfo.append("-values[" + i + "] = " + values[i] + "\n");
			tvMsg.setText(sensorInfo);
		}
		
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			//當感應器的精準度改變時會呼叫此方法 			
		}
	};

	@Override
	protected void onResume() {
		super.onResume();				
		sensorMgr.registerListener(listener, 
					sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					SensorManager.SENSOR_DELAY_UI);	
	}

	@Override
	protected void onPause() {
		super.onPause();		
		sensorMgr.unregisterListener(listener);
	}
}
