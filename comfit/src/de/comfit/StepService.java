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
 * Z�hlt die Schritte und pr�ft ob eine �bergebene Challenge erledigt wurde
 * 
 * @author Comtec
 * 
 */
public class StepService extends Service implements SensorEventListener {

	public static final String STEPSTODO = "stepstodo";

   public static final String DONE_STEPS = "doneSteps";

   // SensorManager Objekt
	SensorManager sensorManager;

	// Anzahl der gemachten Schritte
	int steps = 0;
	int progress = 0;
	int schritteZuMachen;
	int stepsCachedBySensor = -1;

	Intent i;

	// Variable die pr�ft ob eine Challenge erfolgreich beendet wurde
	boolean challengeIsNotDone = true;

	private int sourceHash;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null) {
			// TODO do something useful
			schritteZuMachen = intent.getIntExtra("schritte", 1);
			sourceHash = intent.getIntExtra("hashcode", 0);
			init();
		}
		return Service.START_STICKY;
	}

	/**
	 * Initialisiert den SensorManager
	 */
	private void init() {
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

		mSensorManager.registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	private SensorManager mSensorManager;
	private Sensor mSensor;

	public void exit() {
		mSensorManager.unregisterListener(this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (stepsCachedBySensor == -1)
			stepsCachedBySensor = (int) Math.round(event.values[0]);
		steps = (int) Math.round(event.values[0]);

		progress = (int) ((steps - stepsCachedBySensor) * 100 / schritteZuMachen);

		Intent intent = new Intent();
		intent.setAction("de.comfit.sport.RunningActiv");
		intent.putExtra(DONE_STEPS, (steps - stepsCachedBySensor));
      intent.putExtra(STEPSTODO,schritteZuMachen);
		intent.putExtra("hashcode", sourceHash);
		sendBroadcast(intent);

		if (progress >= 100) {
			exit();
			stopSelf();
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}
}