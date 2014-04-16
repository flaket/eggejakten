package io.flakstad.eggejakten;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorActivity extends Activity implements SensorEventListener {

	private SensorManager mSensorManager;
	private Sensor mPressure, mTemperature, mLight;
	private float millibars_of_pressure;
	private float temperature_in_celsius;
	private float light_in_lx;

	TextView textView;

	@Override
	public final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sensor);
		// Get an instance of the sensor service, and use that to get an
		// instance of
		// a particular sensor.
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
		mTemperature = mSensorManager
				.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
		mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		float value = event.values[0];
		if (event.sensor.equals(mPressure)) {
			setMillibars_of_pressure(value);
			textView = (TextView) findViewById(R.id.pressure);
			textView.setText(Float.toString(value));
		} else if (event.sensor.equals(mTemperature)) {
			setTemperature_in_celsius(value);
			textView = (TextView) findViewById(R.id.temperature);
			textView.setText(Float.toString(value));
		} else if (event.sensor.equals(mLight)) {
			setLight_in_lx(value);
			textView = (TextView) findViewById(R.id.light);
			textView.setText(Float.toString(value));
		}

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onResume() {
		// Register a listener for the sensor.
		super.onResume();
		mSensorManager.registerListener(this, mPressure,
				SensorManager.SENSOR_DELAY_NORMAL);
		mSensorManager.registerListener(this, mTemperature,
				SensorManager.SENSOR_DELAY_NORMAL);
		mSensorManager.registerListener(this, mLight,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		// Be sure to unregister the sensor when the activity pauses.
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

	public float getMillibars_of_pressure() {
		return millibars_of_pressure;
	}

	public void setMillibars_of_pressure(float millibars_of_pressure) {
		this.millibars_of_pressure = millibars_of_pressure;
	}

	public float getTemperature_in_celsius() {
		return temperature_in_celsius;
	}

	public void setTemperature_in_celsius(float temperature_in_celsius) {
		this.temperature_in_celsius = temperature_in_celsius;
	}

	public float getLight_in_lx() {
		return light_in_lx;
	}

	public void setLight_in_lx(float light_in_lx) {
		this.light_in_lx = light_in_lx;
	}

}
