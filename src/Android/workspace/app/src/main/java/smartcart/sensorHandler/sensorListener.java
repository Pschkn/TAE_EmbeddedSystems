package smartcart.sensorHandler;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.drive.query.internal.MatchAllFilter;

import smartcart.activities.R;

/**
 * Created by admin on 02.04.2017.
 */

public class sensorListener implements SensorEventListener {
    //--- Temporary SensorVariables
    float lastAzimut;
    float lastPitch;
    float lastRoll;

    boolean orientationSet;
    boolean accelerationSet;

    float lastAx;
    float lastAy;
    float lastAz;

    int state = 0;

    long stateStartedTime;
    final long timeBetweenStates = 1000;

    boolean calculationRunning = false;
    //--- -------------------------

    MediaPlayer mp;
    int currentVolume = 1;
    boolean inUse = true;


    //---
    float[] mGravity;
    float[] mGeomagnetic;

    public sensorListener(MediaPlayer mp){
        this.mp = mp;

    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(calculationRunning) return;

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            mGravity = event.values;

            lastAx = mGravity[0];
            lastAy = mGravity[1];
            lastAz = mGravity[2];

            accelerationSet = true;
        }

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                lastAzimut = orientation[0]; // orientation contains: azimut, pitch and roll
                lastPitch = orientation[1];
                lastRoll = orientation[2];

                orientationSet = true;
            }
        }
        // --- Calculation running True --- //
        calculationRunning = true;

        checkForStateReset();

        if(accelerationSet && orientationSet){
            processData();
        }

        calculationRunning = false;
        // --- Calculation running False -- //
        return;
    }
int i=0;
    private void processData(){
        //Log.i("CalcNo: ", String.valueOf(i++));
        // calculation
        float currentAx = calcAx(lastAx, lastAy, lastAz, lastRoll, lastAzimut);
        float currentAz = calcAz(lastAx, lastAy, lastAz, lastPitch, lastRoll);
        //Log.i("Current Ax/Az: ", String.valueOf(currentAx)+ "/" + String.valueOf(currentAz));
        checkIfnextState(currentAx, currentAz);
    }

    private float calcAx(float axh, float ayh, float azh, float roll, float azimut){
        //float currentAx = (float)(axh/(Math.cos(roll)*Math.cos(azimut)));

        float top = axh * (float)(1 - Math.tan(roll*3.141/180)*azh - Math.tan(azimut*3.141/180)*ayh);
        float bot = (float)(Math.cos(azimut*3.141/180) * Math.cos(roll*3.141/180));

        float currentAx = top/bot;

        if(currentAx>200)
            return 0;
        return currentAx;
    }

    private float calcAz(float axh, float ayh, float azh, float pitch, float roll){
        //float currentAz = (float)(azh/(Math.cos(roll)*Math.cos(pitch)));

        float top = azh * (float)(1 - Math.tan(roll*3.141/180)*axh - Math.tan(pitch*3.141/180)*ayh);
        float bot = (float)(Math.cos(pitch*3.141/180) * Math.cos(roll*3.141/180));

        float currentAz = top/bot;

        return currentAz;
    }

    private void checkIfnextState(float currentAx, float currentAz){
        switch (state){
            case 0:
                Log.i("State 0: Ax/Az", String.valueOf(currentAx) + "/" + String.valueOf(currentAz));
                if(currentAx > 10 && currentAx < 30
                        && currentAz > 23 && currentAz < 43){
                    nextState();
                }
                break;
            case 1:
                Log.i("State 1: Ax/Az", String.valueOf(currentAx) + "/" + String.valueOf(currentAz));
                if(currentAx > -5 && currentAx < 5
                        && currentAz > -5 && currentAz < 5){
                    nextState();
                }
                break;
            case 2:
                Log.i("State 2: Ax/Az", String.valueOf(currentAx) + "/" + String.valueOf(currentAz));
                if(currentAx > -30 && currentAx < -10
                        && currentAz > -25 && currentAz < -5){
                    nextState();
                }
                break;
            case 3:
                Log.i("State 3: Ax/Az", String.valueOf(currentAx) + "/" + String.valueOf(currentAz));
                if(currentAx > -5 && currentAx < 5
                        && currentAz > -5 && currentAz < 5){
                    nextState();
                }
                break;
            case 4:
                if(currentAx > 10 && currentAx < 30
                        && currentAz > 45 && currentAz < 55){
                    nextState();
                }
                break;
            default:
                break;
        }

    }

    private void nextState(){
        state++;
        if (state<5){ // another valid state
            stateStartedTime = System.nanoTime();
        }
        else{
            Log.i("DONE", "WOHO");
            state = 0;
        }
    }

    private void checkForStateReset(){
        if (state == 0) return;
        long currentlyPassedTime = (System.nanoTime() - stateStartedTime) / 1000000;
        if(currentlyPassedTime > timeBetweenStates)
            state = 0;
        return;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
