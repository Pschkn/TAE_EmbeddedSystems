package smartcart.sensorHandler;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.drive.query.internal.MatchAllFilter;

import smartcart.activities.R;
import smartcart.activities.goShoppingDefaultFragment;
import smartcart.model.shoppingList;

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

    boolean newAccelValue = false;

    long stateStartedTime;
    final long timeBetweenStates = 200;

    boolean calculationRunning = false;

    boolean xValueReached = false;
    boolean zValueReached = false;
    //--- -------------------------
    goShoppingDefaultFragment parentFragment;

    MediaPlayer mp;
    int currentVolume = 1;
    boolean inUse = true;


    //---
    float[] mGravity;
    float[] mGeomagnetic;

    // testwerte
    boolean testBoolPlusZuerst = false;
    boolean testBoolMinusZuerst = false;

    public sensorListener(MediaPlayer mp){
        this.mp = mp;
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized (this) {

            if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
                mGravity = event.values;
            }

            if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
                float[] mAccel = event.values;
                Log.i("MyTimestamp", String.valueOf(event.timestamp));
                lastAx = mAccel[0];
                lastAy = mAccel[1];
                lastAz = mAccel[2];

                accelerationSet = true;
                newAccelValue = true;
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

            if (accelerationSet && orientationSet && newAccelValue) {
                processData();
                newAccelValue = false;
            }

            calculationRunning = false;
            // --- Calculation running False -- //
        }
        return;
    }

    private void processData(){

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
        Log.i("UnserKreis: ", String.valueOf(currentAx) + "/" + String.valueOf(currentAz) +
                "#" + lastAx + "#" + lastAy + "#" + lastAz);
        switch (state){
            case 0:
                Log.i("State 0: Ax/Az", String.valueOf(currentAx) + "/" + String.valueOf(currentAz) +
                        "#" + lastAx + "#" + lastAy + "#" + lastAz);

                if (currentAx < +100 && currentAx > +5)
                    xValueReached = true;
                if (currentAz > 1 && currentAz < 100)
                    zValueReached = true;
                if (xValueReached && zValueReached){
                    nextState();
                }
                break;
            case 1:
 //               Log.i("State 1: Ax/Az", String.valueOf(currentAx) + "/" + String.valueOf(currentAz));
                if (currentAx < +100 && currentAx > +5)
                    xValueReached = true;
                if (currentAz > -100 && currentAz < -3)
                    zValueReached = true;
                if (xValueReached && zValueReached){
                    nextState();
                }
                break;
            case 2:
                Log.i("State 2: Ax/Az", String.valueOf(currentAx) + "/" + String.valueOf(currentAz) +
                "#" + lastAx + "#" + lastAy + "#" + lastAz);

                if (currentAx < -5 && currentAx > -100)
                    xValueReached = true;
                if (currentAz > -100 && currentAz < -3)
                    zValueReached = true;
                if (xValueReached && zValueReached){
                    nextState();
                }
                break;
            case 3:
                Log.i("State 3: Ax/Az", String.valueOf(currentAx) + "/" + String.valueOf(currentAz) +
                "#" + lastAx + "#" + lastAy + "#" + lastAz);

                if (currentAx < -5 && currentAx > -100)
                    xValueReached = true;
                if (currentAz > 3 && currentAz < 100)
                    zValueReached = true;
                if (xValueReached && zValueReached){
                    nextState();
                }
                break;
            default:
                break;
        }

    }

    private void nextState(){
        Log.i("NextState", String.valueOf(state));
        state++;
        xValueReached = false;
        zValueReached = false;
        if (state <= 3){ // another valid state
            stateStartedTime = System.nanoTime();
        }
        else{
            shoppingList.BuyItem(0);
            parentFragment.UpdateMe();
            state = 0;
        }
    }

    private void checkForStateReset(){
        if (state == 0) return;
        long currentlyPassedTime = (System.nanoTime() - stateStartedTime) / 1000000;
        if(currentlyPassedTime > timeBetweenStates) {
            state = 0;
            xValueReached = false;
            zValueReached = false;
        }
        return;
    }

    public void setFragment(goShoppingDefaultFragment fragment){
        parentFragment = fragment;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
