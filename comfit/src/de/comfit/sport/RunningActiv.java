package de.comfit.sport;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import de.comfit.SportActivity;
import de.comfit.StepService;

public class RunningActiv extends SportActiv {
	public static final int CAL_PER_STEP = 1;
	private int steps;

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


	@Override
	public double getCalStep(double weight) {
		// TODO Auto-generated method stub
		return 0.5;
	}

	@Override
	public void start(View source) {
		// TODO Auto-generated method stub
		this.setSource(source);
		disableOthers();
		setSteps(5);
		start();
	}

	// TODO call right updateProgress Method and update progressbar
	@Override
	public void onReceive(Context context, Intent intent) {
		SportActiv activ = super.getSportActivByHash(intent.getIntExtra(
				"hashcode", 0));
		int stepsDone = intent.getIntExtra("doneSteps", 1);
		activ.updateProgress(
				stepsDone * 100 / ((RunningActiv) activ).getSteps(), stepsDone,
				activ);
		int stepsTodo = intent.getIntExtra(StepService.STEPSTODO, 1);
		
		
		
		
	}

	@Override
	public int getMaxWorkoutSize() {
		// TODO Auto-generated method stub
		return 10000;
	}
	
	@Override
	public int getMinWorkoutSize() {
		// TODO Auto-generated method stub
		return 10;
	}
	
	@Override
	public void setWorkoutSize(int size) {
		// TODO Auto-generated method stub
		steps = size;
	}

   @Override
   public String getLabel()
   {
      // TODO Auto-generated method stub
      return "running "  + steps;
   }
}
