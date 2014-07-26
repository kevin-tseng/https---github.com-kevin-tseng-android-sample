package org.lightEx;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class LightEx extends Activity {
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
		float[] values = event.values;
		StringBuilder sensorInfo = new StringBuilder();
		sensorInfo.append("sensor name: " + sensor.getName() + "\n");
		sensorInfo.append("sensor type: " + sensor.getType() + "\n");
		sensorInfo.append("used power: " + sensor.getPower() + " mA\n");
		sensorInfo.append(getString(R.string.maxRange) + 
					sensor.getMaximumRange() + "\n");
		sensorInfo.append("values[0] = " + values[0] + "\n");			

		if(values[0] >= 10000)
			sensorInfo.append(getString(R.string.anyThing));
		else if(values[0] >= 7000)
			sensorInfo.append(getString(R.string.operation));
		else if(values[0] >= 500)
			sensorInfo.append(getString(R.string.read));
		else if(values[0] >= 100)
			sensorInfo.append(getString(R.string.dailyLife));
		else if(values[0] >= 30)
			sensorInfo.append(getString(R.string.watchTV));
		else if(values[0] >= 5)
			sensorInfo.append(getString(R.string.walk));
		else
			sensorInfo.append(getString(R.string.sleep));

		tvMsg.setText(sensorInfo);
	}
	
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}
};

@Override
protected void onResume() {
	super.onResume();				
	sensorMgr.registerListener(listener, 
				sensorMgr.getDefaultSensor(Sensor.TYPE_LIGHT),
				SensorManager.SENSOR_DELAY_UI);		
}

	@Override
	protected void onPause() {
		super.onPause();		
		sensorMgr.unregisterListener(listener);
	}
}
