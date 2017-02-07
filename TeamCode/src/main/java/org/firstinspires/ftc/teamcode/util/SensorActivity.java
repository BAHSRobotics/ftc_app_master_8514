/*
package org.firstinspires.ftc.teamcode.util;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.Settings;

import static android.R.attr.gravity;

//import org.firstinspires.ftc.teamcode.R;

public class SensorActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor accelerometer;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main)

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        //Left turn + right turn - if camera is to left
        float ax = event.values[0];
        float ay = event.values[1];
        float az = event.values[2];
        final double alpha = 0.8;
        double gx = alpha * gravity + (1 - alpha) * event.values[0];
        double gy = alpha * gravity + (1 - alpha) * event.values[1];
        if (ax == gx || ax == -gx) { // If phone is horizontal
            if ()// If robot continues in loop
        }
        if (ay == gy || ay == -gy) { // If phone is vertical
            // If robot continues in loop
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}*/
