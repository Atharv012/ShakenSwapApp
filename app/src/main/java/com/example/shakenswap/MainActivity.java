package com.example.shakenswap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    int the_imp_number=0;
    ImageView imageView1;
    ImageView imageView2;
    private long mShakeTimestamp=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView2 = findViewById(R.id.imageView2);
        imageView1 = findViewById(R.id.imageView1);

        SensorManager sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor accMetre = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        sensorManager.registerListener(this,accMetre,SensorManager.SENSOR_DELAY_NORMAL );

        Toast.makeText(this, "Shake to swap the colors", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;

            float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            if (gForce > 2.0f) {
                final long now = System.currentTimeMillis();
                // ignore shake events too close to each other (500ms)
                if (mShakeTimestamp + 3000> now) {
                    return;
                }

                // reset the shake count after 3 seconds of no shakes
                if (mShakeTimestamp + 3000 < now) {
                    the_imp_number ++;
                }
                if (the_imp_number % 2 == 1) {

                    imageView2.setImageResource(R.drawable.ic_launcher_background);
                    imageView1.setImageResource(R.drawable.ic_launcher_foreground);
                } else {

                    imageView1.setImageResource(R.drawable.ic_launcher_background);
                    imageView2.setImageResource(R.drawable.ic_launcher_foreground);


                } mShakeTimestamp =now;
                Toast.makeText(this, "Colors Swapped Successfully", Toast.LENGTH_SHORT).show();


            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    
}