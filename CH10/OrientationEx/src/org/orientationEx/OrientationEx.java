package org.orientationEx;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class OrientationEx extends Activity {
	private SensorManager sensorMgr;
	private TextView tvMsg;
	float[] magnitude_values = null;
	float[] accelerometer_values = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
		findViews();
	}

	private void findViews() {
		tvMsg = (TextView) findViewById(R.id.tvMsg);
	}

	SensorEventListener listener = new SensorEventListener() {
		public void onSensorChanged(SensorEvent event) {
			switch (event.sensor.getType()) {
				case Sensor.TYPE_ACCELEROMETER:
					accelerometer_values = (float[]) event.values.clone();
					break;
				case Sensor.TYPE_MAGNETIC_FIELD:
					magnitude_values = (float[]) event.values.clone();
					break;
				default:
					break;
			}

			if (magnitude_values != null && accelerometer_values != null) {
				float[] R = new float[9];
				float[] values = new float[3];
				SensorManager.getRotationMatrix(R, null,
						accelerometer_values, magnitude_values);			
				SensorManager.getOrientation(R, values);
				StringBuilder sensorInfo = new StringBuilder();
				for (int i = 0; i < values.length; i++)
					sensorInfo.append("-values[" + i + "] = " + values[i] + "\n");
				tvMsg.setText(sensorInfo);
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {}
	};

	@Override
	protected void onResume() {
		super.onResume();
		if(!(sensorMgr.registerListener(listener, sensorMgr
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_UI) &&
			 sensorMgr.registerListener(listener, sensorMgr
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
				SensorManager.SENSOR_DELAY_UI))){
			Log.w("OrientationEx", "sensor not found!");
			sensorMgr.unregisterListener(listener);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		sensorMgr.unregisterListener(listener);
	}
}
