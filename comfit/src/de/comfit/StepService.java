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
import android.widget.Toast;
import de.comfit.sport.RunningActiv;
import de.comfit.sport.SportActiv;

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

	private int sourceHash;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null) {
			// TODO do something useful
			schritteZuMachen = intent.getIntExtra("schritte", 1);
			sourceHash = intent.getIntExtra("hashcode", 0);
			init();
			Log.d("de.comfit", "start");
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
		// TODO Auto-generated method stub
		if (stepsCachedBySensor == -1)
			stepsCachedBySensor = (int) Math.round(event.values[0]);
		steps = (int) Math.round(event.values[0]);

		progress = (int) ((steps-stepsCachedBySensor)*100 / schritteZuMachen);
		
		Intent intent = new Intent();
		intent.setAction("de.comfit.sport.RunningActiv");
		intent.putExtra("doneSteps", (steps-stepsCachedBySensor));
		intent.putExtra("hashcode", sourceHash);
		sendBroadcast(intent);
		
		if (progress >= 100) {
			createShareDialog();
			exit();
			stopSelf();
		}
	}

	private void createShareDialog() {
		// TODO Auto-generated method stub
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);
 
			// set title
			alertDialogBuilder.setTitle("Share on Twitter?");
 
			// set dialog message
			alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						Intent i= new Intent(getApplicationContext(), TweetActivity.class);
						i.putExtra("message", "Yuhu ... ich habe "+steps+" Schritte gemacht ;) ");
						startActivity(i);
					}
				  })
				.setNegativeButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
			}
	

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}
}