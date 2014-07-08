package de.comfit;

import de.comfit.sport.SitUpActiv;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

public class SitUpService extends Service implements SensorEventListener {
    private boolean unten = false;
    private boolean oben = false;
    private int updateOnlySecondOne = 0;
    private int anzahlSitUps = 0;
    
    //StepAktiv Objekt
  	SitUpActiv obj=null;
  	//Anzahl der gemachten Schritte
  	int progress=0;
  	int sitUpsZuMachen;
  	
  	//Variable die pr�ft ob eine Challenge erfolgreich beendet wurde
  	boolean challengeIsNotDone=true;
  	
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            beschleunigung(sensorEvent);
        }
    }

    @Override
	  public int onStartCommand(Intent intent, int flags, int startId) {
	    //TODO do something useful
		obj=(SitUpActiv)intent.getParcelableExtra("sportactiv");
		obj.start();
		calculateProgress();
	    return Service.START_NOT_STICKY;
	  }

    
    private void beschleunigung(SensorEvent event) {
            float[] werte = event.values;
            float z = werte[2];
            int Z = (int) z;
            if(Z >= -2){
            	oben = true;
            }else if(Z <= -6){
            	unten = true;
            }
            if(unten && oben){
            	unten = false;
            	oben = false;
                updateOnlySecondOne = updateOnlySecondOne + 1;
            }
            if(updateOnlySecondOne == 2){
                updateOnlySecondOne = 0;
                anzahlSitUps  = anzahlSitUps +1;
            }

    }
    
    /**
	 * Berechnet den Fortschritt der �bergebenen Challenge
	 */
	private void calculateProgress() {
		while(challengeIsNotDone){
			progress=(int)(anzahlSitUps/(sitUpsZuMachen/100));
			if(anzahlSitUps>(sitUpsZuMachen/100)){
				obj.updateProgress(progress);
				if(anzahlSitUps>=sitUpsZuMachen){
					challengeIsNotDone=false;
					obj.updateProgress(progress);
					obj.complete();
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
}
