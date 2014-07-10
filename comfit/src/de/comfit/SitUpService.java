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

public class SitUpService extends Service implements SensorEventListener {
	private boolean unten = false;
	private boolean oben = false;
	private int updateOnlySecondOne = 0;
	private int anzahlSitUps = 0;

	// Anzahl der gemachten Schritte
	int progress = 0;
	int sitUpsZuMachen;

	// Variable die prŸft ob eine Challenge erfolgreich beendet wurde
	boolean challengeIsNotDone = true;

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			beschleunigung(sensorEvent);
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		sitUpsZuMachen = intent.getIntExtra("situps", 1);
		init();

		return Service.START_STICKY;
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
		progress = (int) ((anzahlSitUps)*100 / sitUpsZuMachen);
		
		Intent intent = new Intent();
		intent.setAction("de.comfit.sport.SitUpActiv");
		intent.putExtra("doneSitUps", anzahlSitUps);
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
