package de.comfit;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import de.comfit.sport.RunningActiv;

/**
 * Zï¿½hlt die Schritte und prŸft ob eine ï¿½bergebene Challenge erledigt wurde
 * 
 * @author Comtec
 * 
 */
public class StepService extends Service implements SensorEventListener {

	// SensorManager Objekt
	SensorManager sensorManager;

	// Anzahl der gemachten Schritte
	int steps = 0;
	int progress = 0;
	int schritteZuMachen;
	int stepsCachedBySensor = -1;
	
	Intent i;

	// Variable die prŸft ob eine Challenge erfolgreich beendet wurde
	boolean challengeIsNotDone = true;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO do something useful
		schritteZuMachen = intent.getIntExtra("schritte", 1);
		
		init();
		
		Log.d("de.comfit", "start");

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
		// TODO Auto-generated method stub
		if (stepsCachedBySensor == -1)
			stepsCachedBySensor = (int) Math.round(event.values[0]);
		steps = (int) Math.round(event.values[0]);

		progress = (int) ((steps-stepsCachedBySensor)*100 / schritteZuMachen);
		
		Intent intent = new Intent();
		intent.setAction("de.comfit.sport.RunningActiv");
		intent.putExtra("doneSteps", (steps-stepsCachedBySensor));
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