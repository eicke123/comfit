package de.comfit;

import de.comfit.sport.StepActiv;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

/**
 * Zählt die Schritte und prüft ob eine übergebene Challenge erledigt wurde
 * @author Comtec
 *
 */
public class StepService extends Service implements SensorEventListener{

	//StepAktiv Objekt
	StepActiv obj=null;
	//SensorManager Objekt
	SensorManager sensorManager;
	
	//Anzahl der gemachten Schritte
	int steps=0;
	int progress=0;
	int schritteZuMachen;
	
	//Variable die prüft ob eine Challenge erfolgreich beendet wurde
	boolean challengeIsNotDone=true;
	
	  @Override
	  public int onStartCommand(Intent intent, int flags, int startId) {
	    //TODO do something useful
		obj=(StepActiv)intent.getParcelableExtra("sportactiv");
		obj.start();
		init();
		calculateProgress();
	    return Service.START_NOT_STICKY;
	  }

	/**
	 * Berechnet den Fortschritt der übergebenen Challenge
	 */
	private void calculateProgress() {
		while(challengeIsNotDone){
			progress=(int)(steps/(schritteZuMachen/100));
			if(steps>(schritteZuMachen/100)){
				obj.updateProgress(progress);
				if(steps>=schritteZuMachen){
					challengeIsNotDone=false;
					obj.updateProgress(progress);
					obj.complete();
				}
			}
		}
		
	}

	/**
	 * Initialisiert den SensorManager
	 */
	private void init() {
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		steps=(int)Math.round(event.values[0]);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	//Getter & Setter
	public StepActiv getObj() {
		return obj;
	}

	public void setObj(StepActiv obj) {
		this.obj = obj;
	}
	
	} 
