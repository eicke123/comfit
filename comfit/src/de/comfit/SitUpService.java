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
import de.comfit.sport.PushUpActiv;

public class SitUpService extends Service implements SensorEventListener {
	private boolean unten = false;
	private boolean oben = false;
	private int updateOnlySecondOne = 0;
	private int anzahlSitUps = 0;

	// Anzahl der gemachten Schritte
	int progress = 0;
	int sitUpsZuMachen = 100;

	// Variable die pr�ft ob eine Challenge erfolgreich beendet wurde
	boolean challengeIsNotDone = true;

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			beschleunigung(sensorEvent);
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO do something useful
		//obj = (PushUpActiv) intent.getParcelableExtra("sportactiv");
		// obj=(PushUpActiv)intent.getParcelableExtra("sportactiv");
		// obj.start();
		
		init();
		
		calculateProgress();
		Log.d("de.comfit", "start");
		return Service.START_NOT_STICKY;
	}

	private void beschleunigung(SensorEvent event) {
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
		if (updateOnlySecondOne == 2) {
			updateOnlySecondOne = 0;
			anzahlSitUps = anzahlSitUps + 1;
			Toast.makeText(getApplicationContext(),
					"anzahlSitups: " + anzahlSitUps, Toast.LENGTH_LONG).show();
		}

	}

	/**
	 * Berechnet den Fortschritt der �bergebenen Challenge
	 */
	private void calculateProgress() {
		while (challengeIsNotDone) {
			progress = (int) (anzahlSitUps / (sitUpsZuMachen / 100));
			if (anzahlSitUps > (sitUpsZuMachen / 100)) {
				if (anzahlSitUps >= sitUpsZuMachen) {
					challengeIsNotDone = false;
				}
			}
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
	
	private void init() {
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		mSensorManager.registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	public void exit() {
		mSensorManager.unregisterListener(this);
	}
}
