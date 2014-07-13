package de.comfit.sport;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import de.comfit.SitUpService;
import de.comfit.SportActivity;

/**
 * Class to hold methods for situp activity
 */
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
		super.setActive();
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
		return 0.2 * weight;
	}

	@Override
	public void start(View source) {
		this.setSource(source);
		disableOthers();
		start();
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// situp activity runs as a servie, so update progressbar while doing
		// the exercise
		SportActiv activ = super.getSportActivByHash(intent.getIntExtra(
				"hashcode", 0));
		int situpsDone = intent.getIntExtra("doneSitUps", 1);
		activ.updateProgress(
				situpsDone * 100 / ((SitUpActiv) activ).getSitups(),
				situpsDone, activ);
	}

	@Override
	public int getMaxWorkoutSize() {
		// maximum 50 situps in each exercise
		return 50;
	}

	@Override
	public int getMinWorkoutSize() {
		// minimum 10 situps in each exercise
		return 10;
	}

	@Override
	public void setWorkoutSize(int size) {
		situps = size;
	}

	@Override
	public int getWorkoutSize() {
		return situps;
	}

	@Override
	public String getLabel() {
		return "make " + situps + " situps";
	}

}
