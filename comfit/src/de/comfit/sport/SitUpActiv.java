package de.comfit.sport;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import de.comfit.SitUpService;
import de.comfit.SportActivity;

public class SitUpActiv extends SportActiv {
	public static final int CAL_PER_SITUP = 1;
	private int situps;
	
	private int source;

	public SitUpActiv(SportActivity context) {
		super(context);
	}

	public SitUpActiv() {
	}

	private void start() {
		source = this.hashCode();
		
		Intent intent = new Intent(context, SitUpService.class);
		intent.putExtra("situps", situps);
		intent.putExtra("hashcode", source);
		context.startService(intent);
	}

	public int getSitups() {
		return situps;
	}

	public void setSitups(int situps) {
		this.situps = situps;
	}

	@Override
	public double getCalStep(double weight) {
		// TODO Auto-generated method stub
		return 0.2;
	}

	@Override
	public void start(View source) {
		// TODO Auto-generated method stub
		this.setSource(source);

		start();
	}

	// TODO call right updateProgress Method and update progressbar
	@Override
	public void onReceive(Context context, Intent intent) {
		SportActiv activ = super.getSportActivByHash(intent.getIntExtra(
				"hashcode", 0));
		int situpsDone = intent.getIntExtra("doneSitUps", 1);
		activ.updateProgress(
				situpsDone * 100 / ((SitUpActiv) activ).getSitups(),
				situpsDone, activ);
	}
	
	@Override
	public int getMaxWorkoutSize() {
		// TODO Auto-generated method stub
		return 50;
	}
	
	@Override
	public int getMinWorkoutSize() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public void setWorkoutSize(int size) {
		// TODO Auto-generated method stub
		situps = size;
	}

}
