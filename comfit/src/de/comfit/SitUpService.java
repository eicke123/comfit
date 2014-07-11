package de.comfit;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

/*
 * Service Class for counting SitUp's
 */
public class SitUpService extends Service implements SensorEventListener {
	// SitUp down
	private boolean unten = false;
	// SitUp up
	private boolean oben = false;
	private int updateOnlySecondOne = 0;
	// SitUp counter
	private int anzahlSitUps = 0;

	// Progress
	int progress = 0;
	// SitUp's to do 
	int sitUpsZuMachen;

	// Var that checks if challenge is done
	boolean challengeIsNotDone = true;

	private int sourceHash;

	/**
	 * Called when the sensor values change
	 */
	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		// Check's sensor 
		if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			// Method for counting situps
			beschleunigung(sensorEvent);
		}
	}

	/**
	 * Called when Service start's
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null) {
			sourceHash = intent.getIntExtra("hashcode", 0);
			// get how much situps the user has to do 
			sitUpsZuMachen = intent.getIntExtra("situps", 1);
			//initialize the sensorManager
			init();
		}

		return Service.START_STICKY;
	}

	/**
	 * Method for counting situps
	 * @param event
	 */
	private void beschleunigung(SensorEvent event) {
		// checks the Z-Value of sensor 
		float[] werte = event.values;
		float z = werte[2];
		int Z = (int) z;
		if (Z >= -2) {
			oben = true;
		} else if (Z <= -6) {
			unten = true;
		}
		if (unten && oben) {
			unten = false;
			oben = false;
			updateOnlySecondOne = updateOnlySecondOne + 1;
		}
		// check if situps is done
		if (updateOnlySecondOne == 2) {
			updateOnlySecondOne = 0;
			anzahlSitUps = anzahlSitUps + 1;
		}
		// calculate progress
		progress = (int) ((anzahlSitUps) * 100 / sitUpsZuMachen);

		//Create Intent with situps to do
		Intent intent = new Intent();
		intent.setAction("de.comfit.sport.SitUpActiv");
		intent.putExtra("doneSitUps", anzahlSitUps);
		intent.putExtra("hashcode", sourceHash);
		sendBroadcast(intent);

		if (progress >= 100) {
			exit();
			stopSelf();
		}

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int i) {
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private SensorManager mSensorManager;
	private Sensor mSensor;

	/**
	 * Initialize the sensorManager and set the Sensor as Acceleration Sensor
	 */
	private void init() {
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		mSensorManager.registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	/**
	 * Unregister the SensorListener
	 */
	public void exit() {
		mSensorManager.unregisterListener(this);
	}
}
