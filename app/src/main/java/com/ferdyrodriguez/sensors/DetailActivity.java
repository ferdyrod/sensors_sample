package com.ferdyrodriguez.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "DetailActivity";
    private SensorManager mSensorManager;
    private Sensor mSensor;

    private TextView name;
    private TextView vendor;
    private TextView version;
    private TextView sensorX;
    private TextView sensorY;
    private TextView sensorZ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initViews();

        Bundle extras = getIntent().getExtras();
        int sensorType = extras.getInt("SENSOR_TYPE");
        Log.d(TAG, "onCreate: sensorType" + sensorType);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(sensorType);

        Log.d(TAG, "onCreate: " + mSensor.getName());

        if(mSensor != null) {
            name.setText(mSensor.getName());
            vendor.setText(mSensor.getVendor());
            version.setText(String.valueOf(mSensor.getVersion()));
        } else {
            Log.d(TAG, "onCreate: Sensor not present");
        }

    }

    private void initViews() {
        name = (TextView) findViewById(R.id.sensor_name);
        vendor = (TextView) findViewById(R.id.sensor_vendor);
        version = (TextView) findViewById(R.id.sensor_version);
        sensorX = (TextView) findViewById(R.id.sensor_x);
        sensorY = (TextView) findViewById(R.id.sensor_y);
        sensorZ = (TextView) findViewById(R.id.sensor_z);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Log.d(TAG, "onSensorChanged: values[0] " + event.values[0]);
        sensorX.setText(String.valueOf(event.values[0]));
        sensorY.setText(String.valueOf(event.values[1]));
        sensorZ.setText(String.valueOf(event.values[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, mSensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
