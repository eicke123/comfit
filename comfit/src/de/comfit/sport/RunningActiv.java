package de.comfit.sport;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import de.comfit.StepService;
import android.content.Intent;

public class RunningActiv extends SportActiv {
	public static final int CAL_PER_STEP = 1;
	private int steps;
	private int percent;

	public RunningActiv(Activity context) {
		super(context);
		steps = 20;
	}
	
	public RunningActiv() {
		steps = 20;
	}
	
	public void start() {		
		Intent intent = new Intent(context, StepService.class);
		intent.putExtra("schritte", steps);
	    context.startService(intent);
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
	public void start(View source) {
		// TODO Auto-generated method stub
		setSteps(20);
		start();
	}

	
	// TODO call right updateProgress  Method and update progressbar
	@Override
	public void onReceive(Context context, Intent intent) {
		int stepsDone = intent.getIntExtra("doneSteps", 1);
		//Toast.makeText(context, "Steps: " + stepsDone, Toast.LENGTH_SHORT).show();
		updateProgress(stepsDone*100/steps);
	}

}
