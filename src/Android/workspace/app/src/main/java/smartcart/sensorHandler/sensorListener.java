package smartcart.sensorHandler;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
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
