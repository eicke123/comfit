package de.comfit.sport;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import de.comfit.PushupActivity;
import de.comfit.SportActivity;

public class PushUpActiv extends SportActiv {

	private int pushups;

	private int source;

	public PushUpActiv(SportActivity context) {
		super(context);
	}

	@Override
	public void start(View source) {
		this.source = this.hashCode();
		Intent intent = new Intent(context, PushupActivity.class);
		intent.putExtra("count", pushups);
		intent.putExtra("hashcode", this.source);
		context.startActivity(intent);

		super.setActive();
		disableOthers();
		this.setSource(source);
	}

	@Override
	public double getCalStep(double weight) {
		// calculate burned calories per pushup according on weight
		// weight pushed, gravity and distance
		double burnedCaloriesPerPushup = ((weight * 0.7) * 9.81 * 0.3) / 4.1868;
		burnedCaloriesPerPushup = burnedCaloriesPerPushup / 1000;
		burnedCaloriesPerPushup = burnedCaloriesPerPushup
				+ burnedCaloriesPerPushup / 2;

		return burnedCaloriesPerPushup;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// after pushup activity has finished, update progress
		SportActiv activ = super.getSportActivByHash(intent.getIntExtra(
				"hashcode", 0));
		int pushupsDone = intent.getIntExtra("pushups", 1);
		activ.updateProgress(
				pushupsDone * 100 / ((PushUpActiv) activ).getPushups(),
				pushupsDone, activ);
	}

	public int getPushups() {
		return pushups;
	}

	public void setPushups(int pushups) {
		this.pushups = pushups;
	}

	@Override
	public int getMaxWorkoutSize() {
		// maximum 50 pushups in each exercise
		return 50;
	}

	@Override
	public int getMinWorkoutSize() {
		// minimum 5 pushups in each exercise
		return 5;
	}

	@Override
	public void setWorkoutSize(int size) {
		pushups = size;
	}

	@Override
	public String getLabel() {
		return "pushup";
	}

	@Override
	public int getWorkoutSize() {
		return pushups;
	}
}
