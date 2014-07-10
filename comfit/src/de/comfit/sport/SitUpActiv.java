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
	}
	
	public SitUpActiv() {
	}
	
	private void start() {		
		Intent intent = new Intent(context, SitUpService.class);
		intent.putExtra("situps", situps);
		intent.putExtra("hashcode", this.hashCode());
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
		this.setSource(source);
		
		setSitups(5);
		start();
	}

	
	// TODO call right updateProgress  Method and update progressbar
	@Override
	public void onReceive(Context context, Intent intent) {
		SportActiv activ = super.getSportActivByHash(intent.getIntExtra("hashcode",0));
		int situpsDone = intent.getIntExtra("doneSitUps", 1);
		activ.updateProgress(situpsDone*100/((SitUpActiv)activ).getSitups(),situpsDone, this);
	}

}
