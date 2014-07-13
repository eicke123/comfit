package de.comfit.sport;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import de.comfit.SportActivity;
import de.comfit.StepService;

public class RunningActiv extends SportActiv {
	public static final int CAL_PER_STEP = 1;
	private int steps;
	private int percent;

	public RunningActiv(SportActivity context) {
		super(context);
	}

	public RunningActiv() {
	}

	public void start() {

		Intent intent = new Intent(context, StepService.class);
		intent.putExtra("schritte", steps);
		intent.putExtra("hashcode", this.hashCode());
		System.out.println(this.hashCode());
		context.startService(intent);
		super.setActive();
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	@Override
	public double getCalStep(double weight) {
		return weight;
	}

	@Override
	public void start(View source) {
		this.setSource(source);
		disableOthers();

		start();
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// running activity runs as a service, so update progressbar at each
		// step the
		// user does.
		SportActiv activ = super.getSportActivByHash(intent.getIntExtra(
				"hashcode", 0));
		int stepsDone = intent.getIntExtra("doneSteps", 1);
		activ.updateProgress(
				stepsDone * 100 / ((RunningActiv) activ).getSteps(), stepsDone,
				activ);
	}

	@Override
	public int getMaxWorkoutSize() {
		// maximum 1000 steps in each exercise
		return 1000;
	}

	@Override
	public int getMinWorkoutSize() {
		// minimum 10 steps in each exercise
		return 10;
	}

	@Override
	public void setWorkoutSize(int size) {
		steps = size;
	}

	@Override
	public String getLabel() {
		return "running " + steps;
	}

	@Override
	public int getWorkoutSize() {
		return steps;
	}

}
