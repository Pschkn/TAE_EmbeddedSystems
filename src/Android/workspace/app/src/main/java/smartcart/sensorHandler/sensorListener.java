package smartcart.sensorHandler;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.util.Log;

import smartcart.activities.R;

/**
 * Created by admin on 02.04.2017.
 */

public class sensorListener implements SensorEventListener {
    MediaPlayer mp;
    int currentVolume = 1;
    Thread handleSensorEvent;
    boolean inUse = true;


    //---
    float[] mGravity;
    float[] mGeomagnetic;

    public sensorListener(MediaPlayer mp){
        this.mp = mp;

    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //handleSensorEvent = new Thread(new handleSensorEvent());
            if (event.values[0] > 6f) {
                Log.i("myTag", "X: " + +event.values[0]);
            }
            if (event.values[1] > 6f) {
                Log.i("myTag", "Y: " + +event.values[1]);
            }
            if (event.values[2] > 6f) {
                Log.i("myTag", "Z: " + event.values[2]);
            }
        }
        else if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            Log.i("Gyros", "X: " + event.values[0]);
            Log.i("Gyros", "Y: " + event.values[1]);
            Log.i("Gyros", "Z: " + event.values[2]);
            Log.i("Gyros", "----");
        }
        else if(event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            Log.i("Gyros_Rot", "X: " + event.values[0]);
            Log.i("Gyros_Rot", "Y: " + event.values[1]);
            Log.i("Gyros_Rot", "Z: " + event.values[2]);
            Log.i("Gyros_Rot", "----");
        }
        else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            Log.i("Magnetic", "X: " + event.values[0]);
            Log.i("Magnetic", "Y: " + event.values[1]);
            Log.i("Magnetic", "Z: " + event.values[2]);
            Log.i("Magnetic", "----");
        }
        else if(event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            Log.i("Gravity", "X: " + event.values[0]);
            Log.i("Gravity", "Y: " + event.values[1]);
            Log.i("Gravity", "Z: " + event.values[2]);
            Log.i("Gravity", "----");
        }

        // --- MARKUS CODE --- //
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                float azimut = orientation[0]; // orientation contains: azimut, pitch and roll
                float pitch = orientation[1];
                float roll = orientation[2];

                Log.i("Markus", "Azimut: " + azimut);
                Log.i("Markus", "Pitch : " + pitch);
                Log.i("Markus", "Roll  : " + roll);
            }
        }


        return;
    }
    public class handleSensorEvent implements Runnable {

        @Override
        public void run() {
            try{
                if(!inUse) {
                    inUse = true;
                    currentVolume += 10;
                    mp.setVolume(currentVolume, currentVolume);
                    System.out.print("LOL");
                    inUse = false;
                }
            }
            catch (Exception e){

            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
