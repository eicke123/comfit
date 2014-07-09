package de.comfit.sport;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import de.comfit.PushupActivity;
import de.comfit.StepService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RunningActiv extends SportActiv {
	public static final int CAL_PER_STEP = 1;
	private int steps;
	private int percent;

	public RunningActiv(Activity context) {
		super(context);
	}
	
	public void start() {
		System.out.println("start");
		Intent intent = new Intent(context, StepService.class);
		intent.putExtra("schritte", 20);
		Log.d("de.comfit", "runn");
		context.startService(intent);
	}

	@Override
	public void updateProgress(int percent) {
		this.setPercent(percent);
		// TODO Auto-generated method stub

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
	public double getCalStep() {
		// TODO Auto-generated method stub
		return 500;
	}

	@Override
	public void complete() {
		// TODO Auto-generated method stub
		
	}

}
