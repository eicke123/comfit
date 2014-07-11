package de.comfit;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

/**
 * Service class for counting steps 
 * 
 * @author Comtec
 * 
 */
public class StepService extends Service implements SensorEventListener {

	public static final String STEPSTODO = "stepstodo";

   public static final String DONE_STEPS = "doneSteps";

   // SensorManager Object
	SensorManager sensorManager;

	// Step counter
	int steps = 0;
	// Progress
	int progress = 0;
	// Steps to do 
	int schritteZuMachen;
	// Steps cached by sensor
	int stepsCachedBySensor = -1;

	//Intent
	Intent i;

	// Var for checking if challenge is done 
	boolean challengeIsNotDone = true;

	private int sourceHash;

	/**
	 * Called on start of Service
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null) {
			schritteZuMachen = intent.getIntExtra("schritte", 1);
			sourceHash = intent.getIntExtra("hashcode", 0);
			init();
		}
		return Service.START_STICKY;
	}

	/**
	 * Init the SensorManager
	 */
	private void init() {
		// Define the sensor as StepCounter
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

		//Register the sensor listener
		mSensorManager.registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	private SensorManager mSensorManager;
	private Sensor mSensor;

	/**
	 * Unregister the sensor listener
	 */
	public void exit() {
		mSensorManager.unregisterListener(this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Called when the sensor values changes
	 */
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (stepsCachedBySensor == -1)
			stepsCachedBySensor = (int) Math.round(event.values[0]);
		// Get steps from step Counter
		steps = (int) Math.round(event.values[0]);

		progress = (int) ((steps - stepsCachedBySensor) * 100 / schritteZuMachen);

		//Create a Intent and put steps into 
		Intent intent = new Intent();
		intent.setAction("de.comfit.sport.RunningActiv");
		intent.putExtra(DONE_STEPS, (steps - stepsCachedBySensor));
		intent.putExtra(STEPSTODO,schritteZuMachen);
		intent.putExtra("hashcode", sourceHash);
		sendBroadcast(intent);

		if (progress >= 100) {
			exit();
			// Stop the Service
			stopSelf();
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}
}