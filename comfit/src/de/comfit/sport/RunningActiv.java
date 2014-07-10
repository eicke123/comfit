package de.comfit.sport;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import de.comfit.SportActivity;
import de.comfit.StepService;
import android.content.Intent;

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
		this.setSource(source);
		
		setSteps(5);
		start();
	}

	
	// TODO call right updateProgress  Method and update progressbar
	@Override
	public void onReceive(Context context, Intent intent) {
		SportActiv activ = super.getSportActivByHash(intent.getIntExtra("hashcode",0));
		int stepsDone = intent.getIntExtra("doneSteps", 1);
		activ.updateProgress(stepsDone*100/((RunningActiv)activ).getSteps());
	}

}
