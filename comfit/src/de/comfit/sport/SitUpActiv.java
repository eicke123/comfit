package de.comfit.sport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import de.comfit.SitUpService;
import de.comfit.StepService;

public class SitUpActiv extends SportActiv {
	public static final int CAL_PER_SITUP = 1;
	private int situps;

	public SitUpActiv(Activity context) {
		super(context);
		situps = 20;
	}
	
	public SitUpActiv() {
		situps = 20;
	}
	
	private void start() {		
		Intent intent = new Intent(context, SitUpService.class);
		intent.putExtra("situps", situps);
	    context.startService(intent);
	}

	public int getSitups() {
		return situps;
	}

	public void setSitups(int situps) {
		this.situps = situps;
	}
	

	@Override
	public double getCalStep() {
		// TODO Auto-generated method stub
		return 500;
	}

	@Override
	public void start(View source) {
		// TODO Auto-generated method stub
		setSitups(20);
		start();
	}

	
	// TODO call right updateProgress  Method and update progressbar
	@Override
	public void onReceive(Context context, Intent intent) {
		int situpsDone = intent.getIntExtra("doneSitUps", 1);
		//Toast.makeText(context, "Steps: " + stepsDone, Toast.LENGTH_SHORT).show();
		updateProgress(situpsDone*100/situps);
	}

}
