package org.orientationLegacyEx;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class OrientationLegacyEx extends Activity {
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
			float azimuth = values[0];
			String compassStr = "";
			if(azimuth >= 315)
				compassStr = "North-Northwest " + azimuth + " degrees";
			else if(azimuth >= 270)
				compassStr = "West-Northwest " + azimuth + " degrees";
			else if(azimuth >= 225)
				compassStr = "West-Southwest " + azimuth + " degrees";
			else if(azimuth >= 180)
				compassStr = "South-Southwest " + azimuth + " degrees";
			else if(azimuth >= 135)
				compassStr = "South-Southeast " + azimuth + " degrees";
			else if(azimuth >= 90)
				compassStr = "East-Southeast " + azimuth + " degrees";
			else if(azimuth >= 45)
				compassStr = "East-Northeast " + azimuth + " degrees";
			else
				compassStr = "North-Northeast " + azimuth + " degrees";
			
			float pitch = values[1];
			float roll = values[2];
			sensorInfo.append("azimuth = " + compassStr + "\n");
			sensorInfo.append("pitch = " + pitch + "\n");
			sensorInfo.append("roll = " + roll + "\n");
			tvMsg.setText(sensorInfo);
		}
		
		public void onAccuracyChanged(Sensor sensor, int accuracy) {}
	};

	@Override
	protected void onResume() {
		super.onResume();				
		sensorMgr.registerListener(listener, 
					sensorMgr.getDefaultSensor(Sensor.TYPE_ORIENTATION),
					SensorManager.SENSOR_DELAY_UI);		
	}

	@Override
	protected void onPause() {
		super.onPause();		
		sensorMgr.unregisterListener(listener);
	}
}
