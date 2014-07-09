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
 * Z�hlt die Schritte und pr�ft ob eine �bergebene Challenge erledigt wurde
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
	int schritteZuMachen = 100;

	// Variable die pr�ft ob eine Challenge erfolgreich beendet wurde
	boolean challengeIsNotDone = true;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO do something useful
		//obj = (RunningActiv) intent.getParcelableExtra("sportactiv");
		// obj=(RunningActiv)intent.getParcelableExtra("sportactiv");
		// obj.start();
		schritteZuMachen = intent.getIntExtra("schritte", 1);
		
		init();
		// calculateProgress();
		Log.d("de.comfit", "start");

		return Service.START_STICKY;
	}

	/**
	 * Berechnet den Fortschritt der �bergebenen Challenge
	 */
	private void calculateProgress() {
		while (challengeIsNotDone) {
			progress = (int) (steps / (schritteZuMachen / 100));
			if (steps > (schritteZuMachen / 100)) {
				if (steps >= schritteZuMachen) {
					challengeIsNotDone = false;
				}
			}
		}
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
		steps = (int) Math.round(event.values[0]);
		Toast.makeText(getApplicationContext(), "Steps: " + steps,
				Toast.LENGTH_SHORT).show();
		progress = (int) (steps*100 / schritteZuMachen);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}


}
